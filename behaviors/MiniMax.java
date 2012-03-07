package behaviors;

import c4utility.*;
import java.util.ArrayList;

public class MiniMax implements IBehavior {
	private class Action {
		public int action;
		public float value;
		
		public Action(int action, float value) {
			this.action = action;
			this.value = value;
		}
		
		public Action() { }
	}
	
	private int playerId;
	
	public MiniMax(int playerId) {
		this.playerId = playerId;
	}
	
	public int nextMove(C4Board board) {
		Action action = minimaxDecision(board, playerId);
		System.out.println("Action: " + action.action);
		
		return action.action;
	}
	
	private float gameValue(int winner) {
		if (winner ==  1) return 1.0f;
		if (winner == -1) return 0.5f;
		if (winner ==  2) return -1.0f;
		
		return Float.NaN;
	}
	
	private C4Board result(C4Board state, int action) {
		C4Board newstate = new C4Board(state.getData());
		newstate.putIn(action, playerId);
		
		return newstate;
	}
	
	private float maxValue(C4Board state) {
		int terminalvalue = state.getWinner();
		if (terminalvalue != -1) {
			return gameValue(terminalvalue);
		}
		
		float v = Float.NEGATIVE_INFINITY;
		
		for (int action : findActions(state)) {
			v = Math.max(v, minValue(result(state, action)));
		}
		
		return v;
	}
	
	private float minValue(C4Board state) {
		int terminalvalue = state.getWinner();
		if (terminalvalue != -1) {
			return gameValue(terminalvalue);
		}
		
		float v = Float.POSITIVE_INFINITY;
		
		for (int action : findActions(state)) {
			v = Math.min(v, maxValue(result(state, action)));
		}
		
		return v;
	}
	
	private Action max(ArrayList<Action> actions) {
		Action max = new Action(-1, Float.NEGATIVE_INFINITY);
		
		for (Action action : actions) {
			if (action.value > max.value) {
				max.action = action.action;
				max.value = action.value;
			}
		}
		
		return max;
	}
	
	private Action min(ArrayList<Action> actions) {
		Action max = new Action(-1, Float.POSITIVE_INFINITY);
		
		for (Action action : actions) {
			if (action.value < max.value) {
				max.action = action.action;
				max.value = action.value;
			}
		}
		
		return max;
	}
	
	private ArrayList<Integer> findActions(C4Board state) {
		ArrayList<Integer> actions = new ArrayList<Integer>();
		
		for (int column = 0; column < state.getWidth(); column++) {
			if (state.canPutInColumn(column)) {
				actions.add(column);
			}
		}
		
		return actions;
	}
	
	private Action minimaxDecision(C4Board state, int playerId) {
		ArrayList<Integer> validactions = findActions(state);
		ArrayList<Action> actionvalues = new ArrayList<MiniMax.Action>();
		
		if (playerId == 1) { // Start with Max
			for (int action : validactions) {
				actionvalues.add(new Action(action, minValue(result(state, action))));
			}
			
			return max(actionvalues);
		}
		else { // Start with Min
			for (int action : validactions) {
				actionvalues.add(new Action(action, maxValue(result(state, action))));
			}
			
			return min(actionvalues);
		}
	}
}