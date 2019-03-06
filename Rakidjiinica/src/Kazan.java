
public class Kazan {
	private static int count = 1;
	private int kolichestvoMaterial;
	private String tipMaterial;
	private int id;
	
	
	public Kazan() {
		this.setKolichestvoMaterial(0);
		this.setTipMaterial("");
		this.id = Kazan.count++;
	}
	
	public Kazan(int kolichestvoMaterial, String tipMaterial) {
		this.setKolichestvoMaterial(kolichestvoMaterial);
		this.setTipMaterial(tipMaterial);
		this.id = Kazan.count++;
	}


	public int getKolichestvoMaterial() {
		return kolichestvoMaterial;
	}


	public void setKolichestvoMaterial(int kolichestvoMaterial) {
		this.kolichestvoMaterial = kolichestvoMaterial;
	}


	public String getTipMaterial() {
		return tipMaterial;
	}


	public void setTipMaterial(String tipMaterial) {
		this.tipMaterial = tipMaterial;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void dobaviMaterial(int kolichestvo) {
		if(kolichestvo <= 0) {
			System.out.println("Nevalidno kolichestvo!");
			return;
		}
		
		this.kolichestvoMaterial += kolichestvo;
	}
	
	
}
