import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Selo {
	
	private List<Kazan> kazaniZaRakiq = new ArrayList<Kazan>(5);
	private ExecutorService rakidjii;
	private volatile int kolkoRakiqESvarena = 0;
	private boolean isShutDown = false;
	private Map<String, Integer> kolichestvoNabraniPlodove = new ConcurrentHashMap<String, Integer>();
	private Map<String, Integer> kolichestvoRakii = new ConcurrentHashMap<String, Integer>();
	
	private static String[] rakidjiiNames = {"Filip", "Ceko", "Petyo", "Nikola", "Genadi"};
	
	public Selo(int broiRakidjii) {
		this.rakidjii = Executors.newFixedThreadPool(broiRakidjii);
		for(int i = 0; i < 5; i++) {
			this.kazaniZaRakiq.add(new Kazan());
		}
		
		this.kolichestvoNabraniPlodove.put("grozde", 0);
		this.kolichestvoNabraniPlodove.put("kaisii", 0);
		this.kolichestvoNabraniPlodove.put("slivi", 0);
		
		this.kolichestvoRakii.put("grozde", 0);
		this.kolichestvoRakii.put("kaisii", 0);
		this.kolichestvoRakii.put("slivi", 0);
	}
	
	
	public boolean imaLiPulenKazan() {
		for(int i = 0; i < this.kazaniZaRakiq.size(); i++) {
			if(this.kazaniZaRakiq.get(i).getKolichestvoMaterial() >= 10) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean imaLiPrazenKazan() {
		for(int i = 0; i < this.kazaniZaRakiq.size(); i++) {
			if(this.kazaniZaRakiq.get(i).getKolichestvoMaterial() < 10) {
				return true;
			}
		}
		
		return false;
	}
	
	public synchronized void pulniKazan(String tipPlod, int kolichestvo) {
		for(int i = 0; i < kazaniZaRakiq.size(); i++) {
			if(this.kazaniZaRakiq.get(i).getKolichestvoMaterial() < 10) {
				
				int oldValue = this.kolichestvoNabraniPlodove.get(tipPlod);
				this.kolichestvoNabraniPlodove.put(tipPlod, oldValue + kolichestvo);
				
				if(this.kazaniZaRakiq.get(i).getKolichestvoMaterial() == 0) {
					this.kazaniZaRakiq.get(i).setTipMaterial(tipPlod);
				} else {
					if(!this.kazaniZaRakiq.get(i).getTipMaterial().equals(tipPlod)) {
						return;
					}
				}
				
				this.kazaniZaRakiq.get(i).dobaviMaterial(kolichestvo);
				if(this.kazaniZaRakiq.get(i).getKolichestvoMaterial() >= 10) {
					
					synchronized (this) {
						this.notifyAll();	
					}
					
					if(this.isShutDown) {
						return;
					}
					System.out.println("[SELO] Ima pulen kazan, davam na nqkoi rakidjiq da vari!");
					this.rakidjii.submit(new Rakidjiq(Selo.rakidjiiNames[new Random().nextInt(Selo.rakidjiiNames.length)],
														new Random().nextInt(45) + 18,
														tipPlod,
														this,
														this.kazaniZaRakiq.get(i)));
				}
				return;
			}
		}
		
		System.out.println("[SELO] Nqma prazni kzani za pulnene!");
	}


	public int getKolkoRakiqESvarena() {
		return kolkoRakiqESvarena;
	}


	public void setKolkoRakiqESvarena(int kolkoRakiqESvarena) {
		this.kolkoRakiqESvarena = kolkoRakiqESvarena;
	}
	
	
	public void printKolichestvoPlodove() {
		for(Entry<String, Integer> e : this.kolichestvoNabraniPlodove.entrySet()) {
			System.out.println(e.getKey() + " -> " + e.getValue());
		}
	}
	public void printKolichestvoRakii() {
		System.out.println("Kolichestvo rakii:");
		for(Entry<String, Integer> e : this.kolichestvoRakii.entrySet()) {
			System.out.println(e.getKey() + " -> " + e.getValue());
		}
	}
	
	public String nayBraniqtPlod() {
		
		String nayBranPlod = "";
		int maxKolichestvo = 0;
		for(Entry<String, Integer> e : this.kolichestvoNabraniPlodove.entrySet()) {
			if(e.getValue() > maxKolichestvo) {
				maxKolichestvo = e.getValue();
				nayBranPlod = e.getKey();
			}
		}
		
		return nayBranPlod;
	}

	public void addKolichestvoRakiq(String tip, int kolichestvo) {
		this.kolichestvoRakii.put(tip, this.kolichestvoRakii.get(tip) + kolichestvo);
	}
	
	public String nayProizvejdanaRakiq() {
		
		String nayProizvejdanaRakiq = "";
		int maxLitri = 0;
		for(Entry<String, Integer> e : this.kolichestvoRakii.entrySet()) {
			if(e.getValue() > maxLitri) {
				maxLitri = e.getValue();
				nayProizvejdanaRakiq = e.getKey();
			}
		}
		
		return nayProizvejdanaRakiq;
	}
	
	public double syotnoshenieGrozdovaKaisieva() {
		return this.kolichestvoRakii.get("kaisii") > 0 ?
				this.kolichestvoRakii.get("grozde") / this.kolichestvoRakii.get("kaisii") : 0.0;
	}
	
	public synchronized void shutDown() {
		if(this.isShutDown) {
			return;
		}
		
		this.rakidjii.shutdown();
		this.isShutDown = true;
	}
	
	
}
