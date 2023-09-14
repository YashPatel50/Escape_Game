package escape.rules;

public class BaseRule implements Rule{

	private RuleID id;
	private int ruleValue;
	
	
	public BaseRule(RuleID id, int value) {
		this.id = id;
		this.ruleValue = value;
	}
	
	@Override
	public RuleID getId() {
		return this.id;
	}

	@Override
	public int getIntValue() {
		return this.ruleValue;
	}

}
