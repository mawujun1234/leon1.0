package com.mawujun.role;

public enum AccessDecisionEnum {
	
	ConsensusBased ,//多数票（允许或拒绝）决定了AccessDecisionManager的结果。平局的投票和空票（全是弃权的）的结果是可配置的
	AffirmativeBased ,//如果有任何一个投票器允许访问，请求将被立刻允许，而不管之前可能有的拒绝决定。
	UnanimousBased;//所有的投票器必须全是允许的，否则访问将被拒绝
}
