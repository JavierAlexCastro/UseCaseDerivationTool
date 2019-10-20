package business_objects;

public class UseCase {
	private String actor;
	private String system;
	private String subsystem;
	private boolean isValid;
	
	/*constructor*/
	public UseCase(String actor, String system, String subsystem) {
		
	}
	
	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}	
	
}
