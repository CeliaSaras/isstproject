package es.upm.dit.isst.events.model;

public enum PHASE_STATE {
	NO_STATE(0),
	NOT_STARTED(1),
	IN_PROGRESS(2),
	FINISHED(3);
	
	private int value;
	private static int numberOfElements  = values().length;
	
	
	PHASE_STATE(int value){
		this.value = value;
				
	}
	public PHASE_STATE next()
	{
		int nextValue = this.value+1;
		nextValue %= numberOfElements;
		for (PHASE_STATE state : values())
		{
			if(state.value == nextValue)
				return state;
		}
		return NO_STATE;
	}
}
