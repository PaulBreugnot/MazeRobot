package qLearning.model;

import java.util.Hashtable;

public abstract class AbstractReward implements Reward {
	protected StateActionPair stateActionPair;
	private static Hashtable<StateActionPair, Integer> HashReward = new Hashtable<>();

	protected static void putHashtableValue(StateActionPair stateActionPair, int value) {
		HashReward.put(stateActionPair, value);
	}

	public int getValue() {
		if (HashReward.get(stateActionPair) == null) {
			setValue(stateActionPair);
		}
		return HashReward.get(stateActionPair);
	}

	public void setValue(StateActionPair stateActionPair) {
		putHashtableValue(stateActionPair, getReward(stateActionPair));
	}
}
