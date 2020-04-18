package org.quarkus.example;

public class Fruit{
    private String name;
    private String description;
    public Fruit(){

    }
    /**
     * @param name
     * @param description
     */
    public Fruit(String name, String description) {
        this.name = name;
        this.description = description;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    void setName(String name) {
        this.name = name;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    void setDescription(String description) {
        this.description = description;
    }
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	@Override
	public String toString() {
		return "Fruit [description=" + description + ", name=" + name + "]";
	}

}
