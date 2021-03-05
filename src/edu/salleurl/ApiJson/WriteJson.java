package edu.salleurl.ApiJson;

import edu.salleurl.Logic.Competicio;
import edu.salleurl.Logic.Fase;
import edu.salleurl.Logic.Rapero;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class WriteJson {
    private boolean escrit = false;

    public WriteJson() {
    }

    public boolean write (String name, String startDate, String endDate, LinkedList<Fase> phases, LinkedList<Rapero> raperos, LinkedList<String> paisos) {
        JSONArray arrayPhases = new JSONArray();
        for (int i = 0; i < phases.size(); i++) {
            JSONObject objCountriesPhases = new JSONObject();
            objCountriesPhases.put("budget", phases.get(i).getBudget());
            objCountriesPhases.put("country", phases.get(i).getCountry());
            arrayPhases.add(objCountriesPhases);
        }


        JSONObject objCompetition = new JSONObject();
        objCompetition.put("name", name);
        objCompetition.put("startDate", startDate);
        objCompetition.put("endDate", endDate);
        objCompetition.put("phases", arrayPhases);

        JSONArray arrayCountries = new JSONArray();
        for (int i = 0; i < paisos.size(); i++) {
            arrayCountries.add(paisos.get(i));
        }

        JSONArray arrayRaperos = new JSONArray();
        for (int i = 0; i < raperos.size(); i++) {
            JSONObject objRaperos = new JSONObject();
            objRaperos.put("realName", raperos.get(i).getRealName());
            objRaperos.put("stageName", raperos.get(i).getStageName());
            objRaperos.put("birth", raperos.get(i).getBirth());
            objRaperos.put("nationality", raperos.get(i).getNationality());
            objRaperos.put("level", raperos.get(i).getLevel());
            objRaperos.put("photo", raperos.get(i).getPhoto());
            arrayRaperos.add(objRaperos);
        }

        JSONObject objTotal = new JSONObject();
        objTotal.put("competition", objCompetition);
        objTotal.put("countries", arrayCountries);
        objTotal.put("rappers", arrayRaperos);

        try {
            FileWriter file = new FileWriter("files/competició.json");
            file.write(objTotal.toJSONString());
            System.out.println("Successfully copied new rapper to file");
            file.flush();
            file.close();
            escrit = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return escrit;
    }
}
