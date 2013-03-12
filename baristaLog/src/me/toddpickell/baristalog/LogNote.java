package me.toddpickell.baristalog;

public class LogNote {

	private Long id;
	private String device;
	private String notes;
	private Integer pre_time;
	private Integer bloom_time;
	private Integer brew_time;
	private Integer temp;
	private Integer tamp;
	private Integer grind;
	private String blend;
	
	public LogNote() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getPre_time() {
		return pre_time;
	}

	public void setPre_time(Integer pre_time) {
		this.pre_time = pre_time;
	}

	public Integer getBloom_time() {
		return bloom_time;
	}

	public void setBloom_time(Integer bloom_time) {
		this.bloom_time = bloom_time;
	}

	public Integer getBrew_time() {
		return brew_time;
	}

	public void setBrew_time(Integer brew_time) {
		this.brew_time = brew_time;
	}

	public Integer getTemp() {
		return temp;
	}

	public void setTemp(Integer temp) {
		this.temp = temp;
	}

	public Integer getTamp() {
		return tamp;
	}

	public void setTamp(Integer tamp) {
		this.tamp = tamp;
	}

	public Integer getGrind() {
		return grind;
	}

	public void setGrind(Integer grind) {
		this.grind = grind;
	}

	public String getBlend() {
		return blend;
	}

	public void setBlend(String blend) {
		this.blend = blend;
	}

}
