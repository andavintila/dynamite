package nl.vu.cs.querypie.reasoner.actions.common;

import nl.vu.cs.ajira.actions.Action;
import nl.vu.cs.ajira.actions.ActionConf;
import nl.vu.cs.ajira.actions.ActionContext;
import nl.vu.cs.ajira.actions.ActionFactory;
import nl.vu.cs.ajira.actions.ActionOutput;
import nl.vu.cs.ajira.actions.ActionSequence;
import nl.vu.cs.ajira.data.types.Tuple;
import nl.vu.cs.ajira.exceptions.ActionNotConfiguredException;

public class ParallelExecutionSchemaOnly extends Action {
	public static void addToChain(int step, ActionSequence actions) throws ActionNotConfiguredException {
		ActionConf a = ActionFactory.getActionConf(ParallelExecutionSchemaOnly.class);
		a.setParamInt(ParallelExecutionSchemaOnly.I_MINIMUM_STEP, step);
		actions.add(a);
	}

	public static final int I_MINIMUM_STEP = 0;

	private int step;

	@Override
	public void registerActionParameters(ActionConf conf) {
		conf.registerParameter(I_MINIMUM_STEP, "step", Integer.MIN_VALUE, false);
	}

	@Override
	public void startProcess(ActionContext context) throws Exception {
		step = getParamInt(I_MINIMUM_STEP);
	}

	@Override
	public void process(Tuple tuple, ActionContext context, ActionOutput actionOutput) throws Exception {
	}

	@Override
	public void stopProcess(ActionContext context, ActionOutput actionOutput) throws Exception {
		ActionsHelper.parallelRunPrecomputedRuleExecutorForAllRules(step, false, actionOutput);
	}
}