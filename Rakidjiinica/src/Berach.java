

public class Berach extends Uchastnik implements Runnable {
	private String tipPlodove;
	private Selo selo;
	
	public Berach(String name, int age, String tipPlodove, Selo selo) {
		super(name, age);
		this.setTipPlodove(tipPlodove);
		this.selo = selo;
	}
	
	
	public String getTipPlodove() {
		return tipPlodove;
	}

	public void setTipPlodove(String tipPlodove) {
		this.tipPlodove = tipPlodove;
	}

	
	public Selo getSelo() {
		return selo;
	}


	public void setSelo(Selo selo) {
		this.selo = selo;
	}


	@Override
	public void run() {
		
		while(true) {
			
			while(!this.selo.imaLiPrazenKazan()) {
				try {
					synchronized (this.selo) {
						if(this.selo.getKolkoRakiqESvarena() >= 10){
							this.selo.shutDown();
							return;
						}
						this.selo.wait(1000);	
					}
				} catch (InterruptedException e) {
					return;
				}
			}
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				return;
			}
			
			System.out.println("[BERACH] Berachyt " + super.getName() + " slaga v kazan " + " 1kg material ot " + this.getTipPlodove());
			this.selo.pulniKazan(this.tipPlodove, 1);
		}
		
	}
	

	
}
