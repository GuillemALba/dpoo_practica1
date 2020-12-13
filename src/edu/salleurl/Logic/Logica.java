package edu.salleurl.Logic;

import edu.salleurl.ApiJson.JsonFileBatalla;
import edu.salleurl.ApiJson.JsonFileCompeticio;

import java.lang.reflect.Array;
import java.util.*;

public class Logica {
    private static final float MIN_PUNTUACIO = 0;
    private static final float MAX_PUNTUACIO = 30;

    private JsonFileCompeticio jsonFileCompeticio;
    private JsonFileBatalla jsonFileBatalla;
    int numerosFase1[];
    int guanyadorBatallaFase[];
    int topBatalla1[];
    String usuari = null;
    String contrincant = null;
    int numBatalla = 1;
    int numFase = 1;

    public Logica (JsonFileCompeticio jsonFileCompeticio, JsonFileBatalla jsonFileBatalla) {
        this.jsonFileCompeticio = jsonFileCompeticio;
        this.jsonFileBatalla = jsonFileBatalla;
    }

    public void fesParelles(String usuari) {
        int topPosicio[];
        this.usuari = usuari;
        this.numerosFase1 = RandomizeArray(0, jsonFileCompeticio.getRappers().size()-1);
        this.guanyadorBatallaFase = new int[numerosFase1.length];
        Random r = new Random();

        if ((numBatalla == 1 || numBatalla == 2) && numFase == 1) {
            parellesFase1();
        }
        /*for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
            System.out.println(jsonFileCompeticio.getRappers().get(i).getPuntuacio());
        }*/

        if (jsonFileCompeticio.getCompetition().getPhases().size() == 2 && numFase == 2) {
            // agafem el top1 i top2 per la batalla final
            topPosicio = getTop1Top2();

            // top1 vs top2
            for (int i = 0; i < 2; i++) {
                int random1 = (int) (Math.random() * MAX_PUNTUACIO);
                jsonFileCompeticio.getRappers().get(topPosicio[i]).setPuntuacio(random1);
            }
        } /*else {
            if (jsonFileCompeticio.getCompetition().getPhases().size() == 3) {
                int guanyadorBatallaFase2[] = new int[2];

                for (int i = 0; i < guanyadorBatallaFase.length; i++) {
                    int random1 = (int) (Math.random() * MAX_PUNTUACIO);
                    //jsonFileCompeticio.getRappers().get(guanyadorBatallaFase[i]).setPuntuacio(random1);
                }

                // agafem el top1 i top2 per la batalla final
                getTop1Top2(top);

                // top1 vs top2
                for (int i = 0; i < top.length; i++) {
                    float random1 = MIN_PUNTUACIO + r.nextFloat() * (MAX_PUNTUACIO - MIN_PUNTUACIO);
                    jsonFileCompeticio.getRappers().get(top[i]).setPuntuacio(random1);
                }
                getTop1Top2(top);
            }
        }*/
        }

        /*
        System.out.println("KO1");
        System.out.println("top1: " + top[1]);
        System.out.println("top1 puntuacio: " + jsonFileCompeticio.getRappers().get(top[1]).getPuntuacio());
        System.out.println("top2: " + top[2]);
        System.out.println("top2 puntuacio: " + jsonFileCompeticio.getRappers().get(top[2]).getPuntuacio());
         */

    // agafem el top1 i top2
    public int[] getTop1Top2() {
        int top[] = new int[2];
        System.out.println(topBatalla1[0] + "-" + topBatalla1[1]);

        for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
            if (jsonFileCompeticio.getRappers().get(i).getPuntuacio() == topBatalla1[0]) {
                top[0] = i;
            }
            if (jsonFileCompeticio.getRappers().get(i).getPuntuacio() == topBatalla1[1]) {
                top[1]= i;
            }
        }
        return top;
    }

    public static int[] RandomizeArray(int a, int b){
        Random rgen = new Random();  // Random number generator
        int size = b-a+1;
        int[] array = new int[size];

        for(int i=0; i< size; i++){
            array[i] = a+i;
        }

        for (int i=0; i<array.length; i++) {
            int randomPosition = rgen.nextInt(array.length);
            int temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }
        return array;
    }

    public void parellesFase1 () {
        int aux = 0;
        for (int i = 0; i < numerosFase1.length; i++) {
            int random1 = (int) (Math.random() * MAX_PUNTUACIO);
            if (jsonFileCompeticio.getRappers().get(numerosFase1[i]).getStageName().equalsIgnoreCase(usuari)) {
                if (i % 2 == 0) {
                    jsonFileCompeticio.getRappers().get(numerosFase1[i]).setPuntuacio(0);
                    jsonFileCompeticio.getRappers().get(numerosFase1[i + 1]).setPuntuacio(0);
                    contrincant = jsonFileCompeticio.getRappers().get(numerosFase1[i + 1]).getStageName();
                    i++;
                } else {
                    jsonFileCompeticio.getRappers().get(numerosFase1[i]).setPuntuacio(0);
                    jsonFileCompeticio.getRappers().get(numerosFase1[i - 1]).setPuntuacio(-aux);
                    contrincant = jsonFileCompeticio.getRappers().get(numerosFase1[i - 1]).getStageName();
                }
            } else {
                jsonFileCompeticio.getRappers().get(numerosFase1[i]).setPuntuacio(random1);
            }
            aux = random1;
        }
    }

    public void jugadorGuanyadorBatallaFase1 () {
        int k = 0;
        for (int j = 0; j < jsonFileCompeticio.getRappers().size(); j++) {
            guanyadorBatallaFase[j] = jsonFileCompeticio.getRappers().get(j).getPuntuacio();
        }
        Arrays.sort(guanyadorBatallaFase);
        for( int i = 0; i < guanyadorBatallaFase.length/2; ++i ) {
            int temp = guanyadorBatallaFase[i];
            guanyadorBatallaFase[i] = guanyadorBatallaFase[guanyadorBatallaFase.length - i - 1];
            guanyadorBatallaFase[guanyadorBatallaFase.length - i - 1] = temp;
        }
        topBatalla1 = Arrays.copyOfRange(guanyadorBatallaFase, 0, guanyadorBatallaFase.length/2);
    }

    public void ranquingCompeticio () {
        int puntuacions[] = new int[jsonFileCompeticio.getRappers().size()];
        String noms[] = new String[jsonFileCompeticio.getRappers().size()];
        boolean trobat = false;

        for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
            puntuacions[i] = jsonFileCompeticio.getRappers().get(i).getPuntuacio();
            noms[i] = jsonFileCompeticio.getRappers().get(i).getStageName();
        }
        Arrays.sort(puntuacions);
        for( int i = 0; i < puntuacions.length/2; ++i ) {
            int temp = puntuacions[i];
            puntuacions[i] = puntuacions[puntuacions.length - i - 1];
            puntuacions[puntuacions.length - i - 1] = temp;
        }

        for (int i = 0; i < jsonFileCompeticio.getRappers().size(); i++) {
            trobat = false;
            if (i > 0 && puntuacions[i] == puntuacions[i-1]) {
                i++;
            }
            for (int j = 0; j < jsonFileCompeticio.getRappers().size() && !trobat; j++) {
                if (jsonFileCompeticio.getRappers().get(j).getPuntuacio() == puntuacions[i]) {
                        System.out.println(i + 1 + ". " + jsonFileCompeticio.getRappers().get(j).getStageName() + " - " + puntuacions[i]);
                }
                else {
                    trobat = true;
                }
            }
        }
    }

    //mostrem la info de la competicio
    public void showCompetiStatus() {
        int opcio = 0;
        do {
            System.out.println("-----------------------------------------------------------");
            System.out.println("Phase: " + numFase + " / " + jsonFileCompeticio.getCompetition().getPhases().size() +  " | Score: " + " | Battle: " + numBatalla + " / 2 " + " | Rival: " + contrincant);
            System.out.println("-----------------------------------------------------------\n");
            System.out.println("1. Start the battle");
            System.out.println("2. Show ranking");
            System.out.println("3. Create profile");
            System.out.println("4. Leave competition");
            opcio = getOptionLobby();
        } while (opcio != 4);

    }
    public int getOptionLobby() {
        int opcio = 0;
            System.out.println("\nChoose an option: ");
            Scanner reader = new Scanner(System.in);
            try {
                opcio = reader.nextInt();
                if (opcio > 4 || opcio < 1) {
                    System.out.println("Nomes pots insertar numeros del 1 al 4.\n");
                }

            } catch (InputMismatchException ime) {
                System.out.println("Nomes pots insertar numeros.\n");
                reader.next();
            }
            switch (opcio) {
                case 1:
                    Batalla batalla = new Batalla(jsonFileBatalla);
                    System.out.println("Batalla feta");
                    switch (numBatalla) {
                        case 1:
                            numBatalla = 2;
                            fesParelles(usuari);
                        break;
                        case 2:
                            jugadorGuanyadorBatallaFase1();
                            numFase = 2;
                            numBatalla = 1;
                            fesParelles(usuari);
                        break;
                    }

                    break;
                case 2:
                    ranquingCompeticio();
                    break;
                case 3:
                    System.out.println("This option is not active yet.");
                    break;
                case 4:
                    System.out.println("Thanks for your visit!");
                    break;
            }
        return opcio;
    }
}
