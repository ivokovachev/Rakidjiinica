import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;


public class WriteStatisticsToFile extends Thread {
	private Selo selo;
	
	
	public WriteStatisticsToFile(Selo selo) {
		this.setSelo(selo);
		this.setDaemon(true);
	}
	
	
	public Selo getSelo() {
		return selo;
	}

	public void setSelo(Selo selo) {
		this.selo = selo;
	}


	@Override
	public void run() {
		
		int counter = 1;
		while(true) {
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				return;
			}
			
			File file = new File(".\\src\\statistics", "statistics" + counter + ".txt");
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try(PrintWriter pw = new PrintWriter(file)) {
				pw.println("Nay-bran plod: " + this.selo.nayBraniqtPlod());
				pw.println("Nay-proizvejdana rakiq: " + this.selo.nayProizvejdanaRakiq());
				pw.println("Syotnoshenie v litri na grozdova/kaisieva rakiq: " + this.selo.syotnoshenieGrozdovaKaisieva());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			
			counter++;
		}
		
	}
	
}