/**
 * 
 */
package org.swissre.report.hierarchy;

/**
 * @author prash
 * This is a POJO class that represents a employee in the organization
 *
 */
public class Employee {
	int id;
	String firstName;
	String lastName;
	float salary;
	int managerId;

	public Employee(int id, String firstName, String lastname, float salarty) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastname;
		this.salary = salarty;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastName;
	}

	public void setLastname(String lastname) {
		this.lastName = lastname;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salarty) {
		this.salary = salarty;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastname=" + lastName + ", salarty=" + salary
				+ ", managerId=" + managerId + "]";
	}

}
