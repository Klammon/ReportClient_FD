import java.io.BufferedReader;
import java.math.BigDecimal;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.text.*;
import java.util.*;

import javax.net.ssl.HttpsURLConnection;
/**c'est plus un test mtn c'est un projet top secret**/
//** ceci est en fait le vrai projet secret **//

//import org.omg.CORBA.NameValuePair;
//import org.omg.CORBA.PUBLIC_MEMBER;
import java.io.DataOutputStream;
import java.io.Writer;
public class Processing {
	 //static String CSV = "C:/Users/Manuel Burgos/Documents/Projet_GestionDeFichiersExcels/FD.csv";
     static String CSV = "FD.csv";
	
	//String curdir = this.class.getResource("." ).getPath(); 
	//static Path currentRelativePath = Paths.get("FD.csv");
	//static String CSV = currentRelativePath.toAbsolutePath().toString();
	//static String CSV = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();

     
     //Paths.get(".").toAbsolutePath().normalize().toString();
	/**Fonction pour lire et afficher le ficher csv + Création de map (si 2 key on le meme nom additionner les vals) + 
	 * Prendre la valeur "temps" du client et la mettre dans la formule de changement d'heure**/
	public static void ReadFile () throws IOException 
	{
		try
		{
			BufferedReader fichier_source = new BufferedReader(new FileReader(CSV));
			String chaine;

			/** string pour changer le séparateur de fin ;;; en ,**/
			String delimiter1 = ";;;";
			String delimiter2 = "";
			boolean isFirstLine = true;

			//Map<Integer,String> Map = new HashMap<Integer,String>();// creation de la map 
			Map<String,Double> ClientMap = new HashMap<>();
			//Map<String,Double> FinalMap = new HashMap<>();

			//int i = 1;
			while((chaine = fichier_source.readLine())!= null)//boucle sur tt les lignes du fichiers
			{
				chaine = chaine.replaceAll(delimiter1, delimiter2);// supprime la séparation ;;; a chaque fin de chaine
				String[] tabChaine = chaine.split(","); // retoune un tableau
				//La première information se trouve à l'indice 0
				if (isFirstLine)
				{
					isFirstLine = false;
				}else{
					if (tabChaine.length >= 6)
					{
						ClientMap = ChangeMap(tabChaine, ClientMap); // appel la fonction
						//System.out.println(ClientMap);
					}
				}
			}
			fichier_source.close();
			CreateFile(ClientMap);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Le fichier est introuvable !"); // sinon
		}
	}


	public static Map<String,Double>ChangeMap(String[] tabChaine, Map<String, Double> ClientMap){

		//Map<String, String> idAC = Map();
		//Double Heures = Map.get(6);
		//String Client = Map.get(tabChaine[2]); //prob ?


		//Double Heures = Double.valueOf(tabChaine[6]);
		try
		{ 
			Double Heures = Double.parseDouble(tabChaine[6]);

			// double Heures = Double.parseDouble( Map.get(tabChaine[6])); // reprend ici fils
			// System.out.println(Map.get(tabChaine[6]));


			//String Heure = Map.get(6);
			//double Heures = Double.parseDouble(Heure); 


			if (ClientMap.containsKey(tabChaine[2])){// exist
				// do stuff	
				double valClient = ClientMap.get(tabChaine[2]);
				ClientMap.replace(tabChaine[2],Heures+ valClient);//ajout des valeurs normal + avant
				//System.out.println(" on passe dans le if\n");
			}


			else //  doesn't exists
			{
				// do stuff
				//Client = ClientMap.get(tabChaine[2]) + Map.get(tabChaine[a]);
				ClientMap.put(tabChaine[2],Heures);//ajout des valeurs + la valeur normal
				//System.out.println(" on passe dans le else\n");
			}
		}
		catch(Exception ex) 
		{

		}
		return ClientMap;
		//System.out.println(ClientMap);
		// System.out.println("tabChaine [Client= " + tabChaine[2] + " , Heure=" + tabChaine[6] + "]");
		//return ClientMap;
	}



	public static void CreateFile ( Map<String, Double> newClientMap)
	{

		String eol = System.getProperty("line.separator");

		try
		{
			Writer writer = new FileWriter("Client_FD.csv");
			for (Map.Entry<String, Double> entry : newClientMap.entrySet())
			{
				String key = entry.getKey();
				Double value = entry.getValue();

				/**Convertion decimal to hour**/
				int hour = value.intValue();
				//Long minutes = Math.round((value-hour)*60);
				Long minutes = Math.round((value-hour)*60);

				writer.append(key + "," + hour+":"+minutes + eol);
				//writer.append(key + "," + value + eol);
				//writer.append('\n');

				//System.out.println("Client = " + key+ ", Heures = " + newClientMap.get(key)+ ", Minute = " + Math.round(value * 60));
				System.out.println("Le Client " + key+ "::: à utilisé = " + hour+ " h " + minutes);
			}



			//generate whatever data you want

			writer.flush();
			writer.close();

		} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}
		//return newClientMap;
	}

}


/**
				Integer val = 12;
				Integer val2 = 7;
				Integer val3 = val+val2;
				System.out.println(val3);
 **/


/**
		public static void CreateFile (String[] tabChaine, Map<String, Double> ClientMap){

			for(String key: ClientMap.keySet())
	        {
	            Double hour = ClientMap.get(key);
	            System.out.println("Client = " + key
	                    + ", Heures = " + ClientMap.get(key)
	                    + ", Minute = " + Math.round(hour * 60));
	        }

		}**/

//0,4 h = (0,4x60) min = 24min
//Integer val = (val x 60) + "min" ;



/**
 * 							0,4 h = (0,4x60) min = 24min
 * 
 * 
 * 							String client = (String)ClientMap.getKey();
							String heures =(String) ClientMap.getValue();
							System.out.println("Key = " + client + ", Value = " + heures);
 */
