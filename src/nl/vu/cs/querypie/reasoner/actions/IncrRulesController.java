package nl.vu.cs.querypie.reasoner.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import nl.vu.cs.ajira.actions.Action;
import nl.vu.cs.ajira.actions.ActionConf;
import nl.vu.cs.ajira.actions.ActionContext;
import nl.vu.cs.ajira.actions.ActionFactory;
import nl.vu.cs.ajira.actions.ActionOutput;
import nl.vu.cs.ajira.actions.CollectToNode;
import nl.vu.cs.ajira.actions.RemoveDuplicates;
import nl.vu.cs.ajira.data.types.TLong;
import nl.vu.cs.ajira.data.types.Tuple;
import nl.vu.cs.ajira.data.types.TupleFactory;
import nl.vu.cs.querypie.storage.inmemory.InMemoryTreeTupleSet;
import nl.vu.cs.querypie.storage.inmemory.InMemoryTupleSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IncrRulesController extends Action {

	static final Logger log = LoggerFactory
			.getLogger(IncrRulesController.class);

	public static final int S_DELTA_DIR = 0;
	public static final int I_STAGE = 1;

	private String deltaDir = null;
	private int stage = 0;
	InMemoryTupleSet currentDelta = null;
	Tuple currentTuple = null;

	@Override
	public void registerActionParameters(ActionConf conf) {
		conf.registerParameter(S_DELTA_DIR, "dir of the update", null, true);
		conf.registerParameter(I_STAGE, "stage computation", 0, false);
	}

	@Override
	public void startProcess(ActionContext context) throws Exception {
		deltaDir = getParamString(S_DELTA_DIR);
		stage = getParamInt(I_STAGE);

		switch (stage) {
		case 1:
			currentDelta = (InMemoryTupleSet) context
					.getObjectFromCache("delta");
			currentTuple = TupleFactory.newTuple(new TLong(), new TLong(),
					new TLong());
			break;
		}
	}

	private void process1(Tuple tuple, ActionContext context,
			ActionOutput actionOutput) {
		tuple.copyTo(currentTuple);
		if (!currentDelta.contains(currentTuple)) {
			currentDelta.add(currentTuple);
			currentTuple = TupleFactory.newTuple(new TLong(), new TLong(),
					new TLong());
		}

	}

	@Override
	public void process(Tuple tuple, ActionContext context,
			ActionOutput actionOutput) throws Exception {
		switch (stage) {
		case 1:
			process1(tuple, context, actionOutput);
		}
	}

	private void stop0(ActionContext context, ActionOutput actionOutput)
			throws Exception {
		// Check if the content of the delta is already in the cache. If it is
		// not, then parse it from the file.
		InMemoryTupleSet delta = (InMemoryTupleSet) context
				.getObjectFromCache("delta");
		if (delta == null) {
			delta = parseTriplesFromFile(deltaDir);
			context.putObjectInCache("delta", delta);
		}

		// Apply all the rules in parallel just once
		List<ActionConf> actions = new ArrayList<ActionConf>();
		actions.add(ActionFactory
				.getActionConf(IncrRulesParallelExecution.class));

		// Collect all the derivations on one node
		ActionConf c = ActionFactory.getActionConf(CollectToNode.class);
		c.setParamStringArray(CollectToNode.TUPLE_FIELDS,
				TLong.class.getName(), TLong.class.getName(),
				TLong.class.getName());
		c.setParamBoolean(CollectToNode.SORT, true);
		actions.add(c);

		// Remove the duplicates
		actions.add(ActionFactory.getActionConf(RemoveDuplicates.class));

		// Update the delta and go to the next stage
		c = ActionFactory.getActionConf(IncrRulesController.class);
		c.setParamInt(I_STAGE, 1);
		actions.add(c);

		actionOutput.branch(actions);
	}

	private void stop1(ActionContext context, ActionOutput actionOutput) {
		currentDelta = null;

		// Now time to proceed to the second stage of computation
	}

	@Override
	public void stopProcess(ActionContext context, ActionOutput actionOutput)
			throws Exception {
		switch (stage) {
		case 0:
			stop0(context, actionOutput);
			break;
		case 1:
			stop1(context, actionOutput);
			break;
		}
	}

	private static InMemoryTupleSet parseTriplesFromFile(String inputFile) {
		InMemoryTreeTupleSet set = new InMemoryTreeTupleSet();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					inputFile)));
			String line = null;
			while ((line = reader.readLine()) != null) {
				// Parse the line
				String[] sTriple = line.split(" ");
				TLong[] triple = { new TLong(), new TLong(), new TLong() };
				triple[0].setValue(Long.valueOf(sTriple[0]));
				triple[1].setValue(Long.valueOf(sTriple[1]));
				triple[2].setValue(Long.valueOf(sTriple[2]));
				set.add(TupleFactory.newTuple(triple));
			}
			reader.close();
		} catch (Exception e) {
			log.error("Error in reading the update", e);
		}
		return set;
	}
}