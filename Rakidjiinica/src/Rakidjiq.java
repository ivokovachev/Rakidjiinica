import java.util.Random;

public class Rakidjiq extends Uchastnik implements Runnable {
	private String tipRakiq;
	private Selo selo;
	private Kazan kazan;
	
	public Rakidjiq(String name, int age, String tipRakiq, Selo selo, Kazan kazan) {
		super(name, age);
		this.setTipRakiq(tipRakiq);
		this.setSelo(selo);
		this.setKazan(kazan);
	}
	
	
	public String getTipRakiq() {
		return tipRakiq;
	}

	public void setTipRakiq(String tipRakiq) {
		this.tipRakiq = tipRakiq;
	}
	
	public Selo getSelo() {
		return selo;
	}

	public void setSelo(Selo selo) {
		this.selo = selo;
	}

	public Kazan getKazan() {
		return kazan;
	}

	public void setKazan(Kazan kazan) {
		this.kazan = kazan;
	}
	

	@Override
	public void run() {
		
		while(true) {
			
			if(this.selo.getKolkoRakiqESvarena() >= 10){
				this.selo.shutDown();
				return;
			}
		
			while(!this.selo.imaLiPulenKazan()) {
				synchronized (this.selo) {
					try {
						this.selo.wait(1000);
					} catch (InterruptedException e) {
						return;
					}	
				}
			}
		
			System.out.println("[RAKIDJIQ] Ima pulen kazan, pochvam da varq!");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				return;
			}
		
			this.kazan.setKolichestvoMaterial(0);
		
			synchronized (this.selo) {
				this.selo.notifyAll();
			}

			synchronized (this.selo) {
				if(this.selo.getKolkoRakiqESvarena() >= 10){
					this.selo.shutDown();
					return;
				}
				int litriRakiq = new Random().nextInt(10) + 1;
				this.selo.addKolichestvoRakiq(this.kazan.getTipMaterial(), litriRakiq);
				this.selo.setKolkoRakiqESvarena(this.selo.getKolkoRakiqESvarena() + litriRakiq);
				System.out.println("[RAKIDJIQ] Rakidjiqta " + super.getName() + " svari " + litriRakiq + "kg " +
									this.getTipRakiq() + " rakiq " + " v kazan nomer " + this.kazan.getId() + "!");	
			}
		
		}
	}
	

	
}
