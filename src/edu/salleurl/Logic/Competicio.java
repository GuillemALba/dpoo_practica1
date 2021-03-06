package edu.salleurl.Logic;

import edu.salleurl.ApiJson.WriteJson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Scanner;

public class Competicio {

    private WriteJson writeJson;

    private String name = null;
    private String startDate = null;
    private String endDate =null;
    private LinkedList<Fase> phases;
    private LinkedList<Rapero> raperos;
    private LinkedList<String> paisos;

    public Competicio () {
        this.phases = new LinkedList<>();
        this.writeJson = new WriteJson();
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public LinkedList<Fase> getPhases() {
        return phases;
    }

    public void setRaperos(LinkedList<Rapero> raperos) {
        this.raperos = raperos;
    }

    public void setPaisos(LinkedList<String> paisos) {
        this.paisos = paisos;
    }

    public void registerRapero () {
        Scanner reader = new Scanner(System.in);
        boolean existeix = false;
        boolean comprovacioData = false;
        String nom;
        String nomArtistic;
        String birthday;
        String pais;

        System.out.println("-----------------------------------------------------------");
        System.out.println("Please, enter your personal information:");
        do {
            System.out.println("- Full name: ");
            nom = reader.nextLine();
            for (int i = 0; i < raperos.size() && !existeix; i++) {
                if (nom.equalsIgnoreCase(raperos.get(i).getRealName())) {
                    System.out.println("This rapper already exists");
                    existeix = true;
                }
            }
        } while (existeix);

        existeix = false;
        do {
            System.out.println("- Artistic name: ");
            nomArtistic = reader.nextLine();
            for (int i = 0; i < raperos.size() && !existeix; i++) {
                if (nomArtistic.equalsIgnoreCase(raperos.get(i).getStageName())) {
                    System.out.println("Already exists a rapper with this artistic name");
                    existeix = true;
                }
            }
        } while (existeix);

        do {
            System.out.println("- Birth date (dd/MM/YYYY): ");
            birthday = reader.nextLine();
            comprovacioData = comprovarData(birthday);
            if (!comprovacioData) {
                comprovarErrorData(birthday);
            }
        } while (!comprovacioData);

        existeix = false;
        do {
            System.out.println("- Country: ");
            pais = reader.nextLine();
            for (int i = 0; i<paisos.size(); i++) {
                if (pais.equalsIgnoreCase(paisos.get(i))) {
                    existeix = true;
                }
            }
            if (!existeix) {
                System.out.println("You can't register this competition. You country is not accepted!");
            }
        } while (!existeix);

        System.out.println("- Level: ");
        String nivellString = reader.nextLine();
        int nivell = Integer.parseInt(nivellString);

        System.out.println("- Photo URL: ");
        String foto = reader.nextLine();

        Rapero rapero2 = new Rapero(nom, nomArtistic, birthday, pais, nivell, foto);
        raperos.add(rapero2);

        if(writeJson.write(name, startDate, endDate, phases, raperos, paisos)) {
            System.out.println("Registration completed!");
            System.out.println("-----------------------------------------------------------");
        }
    }

    public String loginRapero () {
        boolean trobat = false;
        String nom = null;
        do {
            System.out.println("-----------------------------------------------------------");
            System.out.println("Enter your artistic name: ");
            Scanner reader = new Scanner(System.in);
            nom = reader.nextLine();
            for (int i = 0; i < raperos.size(); i++) {
                if (nom.equalsIgnoreCase(raperos.get(i).getStageName())) {
                    System.out.println("Correct login!");
                    trobat = true;
                }
            }
            if (trobat == false) {
                System.out.println("Yo' bro, there's no " + nom + " in ma' list.\n");
            }
        } while (trobat == false);
        return nom;
    }

    public static boolean comprovarData (String dia) {
        try {
            SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
            formatData.setLenient(false);
            formatData.parse(dia);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Comprovem d'on ve l'error de la data introduïda
     * @param dia
     */
    public void comprovarErrorData (String dia) {
        int dia2 = Integer.parseInt(dia.split("/")[0]);
        int mes = Integer.parseInt(dia.split("/")[1]);
        int any = Integer.parseInt(dia.split("/")[2]);
        if (mes == 2) {
            if ((any % 4 == 0 && any % 100 != 0) || (any % 400 == 0)) {
                if (dia2 > 29) {
                    System.out.println("\nError, aquest any el mes de febrer té 29 dies");
                }
            }
            else {
                if (dia2 > 28) {
                    System.out.println("\nError, aquest any el mes de febrer té 28 dies");
                }
            }
        }
        if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
            if (dia2 > 31) {
                System.out.println("\nError, aquest mes té 31 dies");
            }
        } else {
            if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
                if (dia2 > 30) {
                    System.out.println("\nError, aquest mes té 30 dies");
                }
            } else {
                if (mes > 12) {
                    System.out.println("\nError, en el paràmetre del mes");
                }
            }
        }
    }
}
