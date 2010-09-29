package flvq;

import java.util.ArrayList;

public class Dado {
	
	private ArrayList<Double> v;
	private String cl;
	
	public Dado(ArrayList<Double> v, String cl){
		this.v = v;
		this.cl = cl;
	}
	
	public Dado(ArrayList<Double> v){
		this.v = v;
		this.cl = null;
	}
	
	public Dado(String cl){
		this.v = null;
		this.cl = cl;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Dado other = (Dado) obj;
		if (cl == null) {
			if (other.cl != null)
				return false;
		} else if (!cl.equals(other.cl))
			return false;
		return true;
	}

	public ArrayList<Double> getV() {
		return v;
	}

	public void setV(ArrayList<Double> v) {
		this.v = v;
	}

	public String getCl() {
		return cl;
	}

	public void setCl(String cl) {
		this.cl = cl;
	}
	
	
}
