package nl.vu.cs.querypie.reasoner.actions;

import java.util.ArrayList;
import java.util.List;

import nl.vu.cs.ajira.actions.ActionConf;
import nl.vu.cs.ajira.actions.ActionContext;
import nl.vu.cs.ajira.actions.ActionOutput;
import nl.vu.cs.ajira.data.types.Tuple;
import nl.vu.cs.querypie.ReasoningContext;

/**
 * A rules controller that execute a single step of materialization based on the
 * facts written on the knowledge base and on the derivation rules.
 * 
 * It writes the newly derived rules in memory (in a cached object)
 */
public class OneStepRulesControllerToMemory extends AbstractRulesController {
	public static final int I_STEP = 0;
	public static final int B_COUNT_DERIVATIONS = 1;

	private int step;
	private boolean countDerivations;

	@Override
	public void registerActionParameters(ActionConf conf) {
		conf.registerParameter(I_STEP, "step", null, true);
		conf.registerParameter(B_COUNT_DERIVATIONS, "count_derivations", false,
				true);
	}

	@Override
	public void process(Tuple tuple, ActionContext context,
			ActionOutput actionOutput) throws Exception {
	}

	@Override
	public void stopProcess(ActionContext context, ActionOutput actionOutput)
			throws Exception {
		List<ActionConf> actions = new ArrayList<ActionConf>();
		if (!ReasoningContext.getInstance().getRuleset()
				.getAllSchemaOnlyRules().isEmpty()) {
			applyRulesSchemaOnly(actions, false, countDerivations,
					Integer.MIN_VALUE);
			applyRulesWithGenericPatternsInABranch(actions, false);
		} else {
			applyRulesWithGenericPatterns(actions, false);
		}
		ActionsHelper.collectToNode(actions);
		actionOutput.branch(actions);
	}

}
