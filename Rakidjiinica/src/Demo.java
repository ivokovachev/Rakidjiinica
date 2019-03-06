import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Demo {

	public static void main(String[] args) {
		
		List<String> names = Arrays.asList("Gosho", "Pesho", "Dragan", "Ivan", "Petkan", "Stoyan", "Milko");
		String[] plodove = {"grozde", "slivi", "kaisii"};
		
		Selo selo = new Selo(4);
		List<Berach> berachi = new ArrayList<Berach>(7);
		List<Thread> berachiThreads = new ArrayList<Thread>(7);
		
		for(int i = 1; i <= 7; i++) {
			berachi.add(new Berach(names.get(new Random().nextInt(names.size())),
							(int)(new Random().nextInt(30) + 18), plodove[new Random().nextInt(plodove.length)], selo));
		}


		for(int i = 0; i < berachi.size(); i++) {
			berachiThreads.add(new Thread(berachi.get(i)));
		}
		
		
		WriteStatisticsToFile statisticsWriter = new WriteStatisticsToFile(selo);
		statisticsWriter.start();
		
		berachiThreads.forEach(thread -> thread.start());
		
		berachiThreads.forEach(thread -> {
			try {
				thread.join();
			} catch (InterruptedException e) {
				return;
			}
		});

		
		
		System.out.println("[SELO] V seloto e svarena tolkova kg rakiq: " + selo.getKolkoRakiqESvarena() + "!");
		selo.printKolichestvoPlodove();
		selo.printKolichestvoRakii();
		
		
		
		
	}

}
