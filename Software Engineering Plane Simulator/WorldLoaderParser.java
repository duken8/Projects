package f16cs350.project.loader.world;

import java.io.InputStream;
import java.util.Scanner;
import java.util.StringTokenizer;

//import f16cs350.project.loader.world.WorldLoader;

public class WorldLoaderParser {
	
	private WorldLoader loader;
	private InputStream stream;
	
	public WorldLoaderParser(WorldLoader loader, InputStream stream)
	{
		if(loader == null || stream == null)
			throw new RuntimeException("Null parameters in WorldLoaderParser constructor");
         
		this.loader = loader;
		this.stream = stream;
	}
	
	public void parse()
	{
		Scanner in = new Scanner(this.stream);
		String line = "", word = "";
		StringTokenizer st;
		
		while(in.hasNextLine())
		{
			line = in.nextLine();
			st = new StringTokenizer(line);
			word = st.nextToken();
			
			switch(word.toUpperCase())
			{
				case "CREATE":
					createCommands(st);
				case "ADD":
					addCommands(st);
				case "DEFINE":
					defineCommands(st);
			}
		}
	}
	
	//create
	private void createCommands(StringTokenizer st)
	{
		String word = st.nextToken();
		switch(word.toUpperCase())
		{
			case "NAVAID":
				createNavaid(st);
			case "AIRPORT":
				createAirport(st);
			case "BUILDING":
				createBuilding(st);
			case "GATE":
				createGate(st);
			case "RAMP":
				createRamp(st);
			case "RUNWAY":
				createRunway(st);
			case "SIGN":
				createSign(st);
			case "TAXIWAY":
				createTaxiway(st);
			case "TOWER":
				createTower(st);
		}
	}
	
	private void createTower(StringTokenizer st)//in progress
	{
		String word = st.nextToken();
	}
	
	private void createTaxiway(StringTokenizer st)//in progress
	{
		String word = st.nextToken();
	}
	
	private void createSign(StringTokenizer st)//in progress
	{
		String word = st.nextToken();
	}
	
	private void createRunway(StringTokenizer st)//in progress
	{
		String word = st.nextToken();
	}
	
	private void createRamp(StringTokenizer st)//in progress
	{
		String word = st.nextToken();
	}
	
	private void createGate(StringTokenizer st)//in progress
	{
		String word = st.nextToken();
	}
	
	private void createBuilding(StringTokenizer st)//in progress
	{
		String word = st.nextToken();
	}
	
	private void createNavaid(StringTokenizer st)
	{
		String word = st.nextToken();
		switch(word.toUpperCase())
		{
			case "AIRWAY":
				createNavaidAirway(st);
			case "FIX":
				createNavaidFix(st);
			case "NDB":
				createNavaidNdb(st);
			case "VOR":
				createNavaidVor(st);
			case "ILS":
				createNavaidIls(st);
		}
	}
	
	private void createNavaidAirway(StringTokenizer st)//in progress
	{
		String word = st.nextToken();//
	}

	private void createNavaidFix(StringTokenizer st)//in progress
	{
		String word = st.nextToken();
	}
	
	private void createNavaidNdb(StringTokenizer st)//in progress
	{
		String word = st.nextToken();
	}
	
	private void createNavaidVor(StringTokenizer st)//in progress
	{
		String word = st.nextToken();
	}
	
	private void createNavaidIls(StringTokenizer st)//in progress
	{
		String word = st.nextToken();
	}
	
	private void createAirport(StringTokenizer st)//in progress
	{
		String word = st.nextToken();
	}
	
	
	
	
	//add
	private void addCommands(StringTokenizer st)
	{
		String word = st.nextToken();
		switch(word.toUpperCase())
		{
			case "AIRPORT":
				addAirportCommand(st);
			case "AIRPORTS":
				addAirportsCommand(st);
			case "NAVAID":
				addNavaidCommand(st);
			case "NAVAIDS":
				addNavaidsCommand(st);	
		}
	}
	
	private void addNavaidsCommand(StringTokenizer st)//in progress
	{
		String word = st.nextToken();//id
	}
	
	private void addNavaidCommand(StringTokenizer st)//in progress
	{
		String word = st.nextToken();//id
	}
	
	private void addAirportCommand(StringTokenizer st)//in progress
	{
		String word = st.nextToken();//id
	}
	
	private void addAirportsCommand(StringTokenizer st)//in progress
	{
		String word = st.nextToken();//id
	}
	
	
	//define polyline
	private void defineCommands(StringTokenizer st)//in progress
	{
		String word = st.nextToken();//polyline
		word = st.nextToken();	
	}
	
	
	
	
	
}