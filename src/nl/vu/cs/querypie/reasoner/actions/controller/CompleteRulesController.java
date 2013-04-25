package nl.vu.cs.querypie.reasoner.actions.controller;

import nl.vu.cs.ajira.actions.ActionConf;
import nl.vu.cs.ajira.actions.ActionContext;
import nl.vu.cs.ajira.actions.ActionFactory;
import nl.vu.cs.ajira.actions.ActionOutput;
import nl.vu.cs.ajira.actions.ActionSequence;
import nl.vu.cs.ajira.data.types.Tuple;
import nl.vu.cs.ajira.exceptions.ActionNotConfiguredException;
import nl.vu.cs.querypie.ReasoningContext;
import nl.vu.cs.querypie.reasoner.actions.common.AbstractRulesController;
import nl.vu.cs.querypie.reasoner.actions.common.ActionsHelper;
import nl.vu.cs.querypie.reasoner.actions.io.TypeStorage;
import nl.vu.cs.querypie.reasoner.support.ParamHandler;

/**
 * A rules controller that execute the complete materialization of all the
 * tuples based on the facts written on the knowledge base and on the derivation
 * rules.
 */
public class CompleteRulesController extends AbstractRulesController {
	public static void addToChain(ActionSequence actions)
			throws ActionNotConfiguredException {
		addToChain(1, actions);
	}

	public static void addToChain(int step, ActionSequence actions)
			throws ActionNotConfiguredException {
		ActionConf c = ActionFactory
				.getActionConf(CompleteRulesController.class);
		c.setParamInt(CompleteRulesController.I_CURRENT_STEP, step);
		actions.add(c);
	}

	@Override
	public void registerActionParameters(ActionConf conf) {
		conf.registerParameter(I_CURRENT_STEP, "I_CURRENT_STEP", null, true);
	}

	public static final int I_CURRENT_STEP = 0;

	private boolean hasDerived;
	private int currentStep;

	@Override
	public void startProcess(ActionContext context) throws Exception {
		hasDerived = false;
		currentStep = getParamInt(I_CURRENT_STEP);
	}

	@Override
	public void process(Tuple tuple, ActionContext context,
			ActionOutput actionOutput) throws Exception {
		hasDerived = true;
	}

	@Override
	public void stopProcess(ActionContext context, ActionOutput actionOutput)
			throws Exception {
		if (hasDerived) {
			ParamHandler.get().setLastStep(currentStep);
			context.incrCounter("Iterations", 1);
			ActionSequence actions = new ActionSequence();
			if (!ReasoningContext.getInstance().getRuleset()
					.getAllSchemaOnlyRules().isEmpty()) {
				currentStep = applyRulesSchemaOnly(actions, TypeStorage.BTREE,
						currentStep);
				currentStep = applyRulesWithGenericPatternsInABranch(actions,
						TypeStorage.BTREE, currentStep);
			} else {
				currentStep = applyRulesWithGenericPatterns(actions,
						TypeStorage.BTREE, currentStep);
			}
			ActionsHelper.collectToNode(ParamHandler.get().isUsingCount(),
					actions);
			CompleteRulesController.addToChain(currentStep, actions);
			actionOutput.branch(actions);
		}
		// } else {
		// // Copy the derivations written on files into the btree
		// ActionSequence actions = new ActionSequence();
		// ActionsHelper.readEverythingFromFiles(ParamHandler.get()
		// .getCopyDir(), actions);
		// ActionsHelper.sort(actions);
		// WriteDerivationsBtree.addToChain(actions);
		// actionOutput.branch(actions);
		// }
	}

}
