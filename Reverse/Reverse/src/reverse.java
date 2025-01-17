/*
██████╗ ███████╗██╗   ██╗███████╗██████╗ ███████╗███████╗
██╔══██╗██╔════╝██║   ██║██╔════╝██╔══██╗██╔════╝██╔════╝
██████╔╝█████╗  ██║   ██║█████╗  ██████╔╝███████╗█████╗  
██╔══██╗██╔══╝  ╚██╗ ██╔╝██╔══╝  ██╔══██╗╚════██║██╔══╝  
██║  ██║███████╗ ╚████╔╝ ███████╗██║  ██║███████║███████╗
╚═╝  ╚═╝╚══════╝  ╚═══╝  ╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝
*/

import extensions.CSVFile;
class reverse extends Program{

    boolean bonneReponse = false;
    int aQuiLeTour = 0;
    int pose = 0;
    boolean aRepondu = false;
    boolean[] input = new boolean[] {false, false, false, false};
    String[] inputkey = new String[] {"F", "G", "H", "J"};
    long timer = getTime();

// Partie
    String csv2string(String file){
        String resultat = "";
        CSVFile csvData = loadCSV(file);
        for(int i=0; i<rowCount(csvData); i++){
            resultat += getCell(csvData, i, 0);
            if(i<rowCount(csvData)-1){
                resultat += '\n';
            }
        }
        return resultat;
    }

    void testString2Int(){
        assertEquals(5, str2Int("5"));
        assertEquals(9, str2Int("9"));
        assertEquals(0, str2Int("0"));
        assertEquals(1, str2Int("10"));
        assertEquals(4, str2Int("41n56so43j"));
    }

    int str2Int(String value){ // Transforme un chifre de type String en int.
        char value2 = charAt(value, 0);
        return (int)value2 - 48;
    }

    QCM[] genererTableauQuestion(){ // Genere un tableau a une dimension, rend les calculs plus rapide.
        CSVFile QCMData = loadCSV("../ressources/QCM.csv",',');
        int tailleTableau = rowCount(QCMData);

        QCM[] tableauQuestion = new QCM[tailleTableau]; // Initalisation du type QCM.

        for(int i=1; i<tailleTableau; i++){
            tableauQuestion[i] = new QCM();
            tableauQuestion[i].numQuestion = str2Int(getCell(QCMData, i, 0));
            tableauQuestion[i].theme = getCell(QCMData, i, 1);
            tableauQuestion[i].difficulte = str2Int(getCell(QCMData, i, 2));
            tableauQuestion[i].question = getCell(QCMData, i, 3);
            tableauQuestion[i].proposition = new String[]{getCell(QCMData, i, 4), getCell(QCMData, i, 5), getCell(QCMData, i, 6), getCell(QCMData, i, 7)};
            tableauQuestion[i].reponse = str2Int(getCell(QCMData, i, 8));
        }
        return tableauQuestion;
    }
// Partie
    joueur genererJoueur(){ // Genere un nouveau joueur ou meme un bot qui reprend le comportement d'un joueur.
        joueur nouveauJoueur = new joueur(); // Initalisation du type joueur.

        nouveauJoueur.nbQuestion = 0;
        nouveauJoueur.nom = "";
        nouveauJoueur.score = 0;

        return nouveauJoueur;
    }

    void testAfficherStatJoueur(){
        joueur joueurN = new joueur();
        joueurN.nbQuestion = 5;
        joueurN.nom = "toto";
        joueurN.score = 40;
        joueurN.ratio = (double)joueurN.score/(double)joueurN.nbQuestion;
        assertEquals("Le joueur toto a un score de 40 et a repondu a 5 question(s) | 8.0p/q", afficherStatJoueur(joueurN));
    }

    String afficherStatJoueur(joueur joueurActuel){
        String resultat = "Le joueur " + joueurActuel.nom + 
        " a un score de " + joueurActuel.score + 
        " et a repondu a " + joueurActuel.nbQuestion + " question(s) | " 
        + joueurActuel.ratio + "p/q"; // Donne un ratio point par question.
        return resultat;
    }

    String  forceTaille(String text, int taille){
        String resultat = "";
        if(length(text) >= taille){
            resultat = substring(text, 0, taille);
        }else{
            for(int i=0; i<taille-length(text); i++){
                resultat += " ";
            }
            resultat += text;
        }

        return resultat;
    }

    String haut(){
        String resultat = "";
        int tailleLigne = 190;

        for(int i=0; i<tailleLigne; i++){
            resultat += "█";
        }
        resultat += "██  ";
        for(int i=0; i<tailleLigne-8; i++){
            resultat += " ";
        }
        resultat += "  ██" + '\n';

        return resultat;
    }

    String plein(){
        String resultat = "";
        int tailleLigne = 190;

        for(int i=0; i<tailleLigne; i++){
            resultat += "█";
        }

        return resultat;
    }

    String centre(String text){
        String resultat = "";
        int tailleLigne = 190;

        resultat += "██  ";
        for(int i=0; i<(tailleLigne - length(text) - 8)/2; i++){
            resultat += "█";
        }
        resultat += text;
        if(length(text)%2!=0){
            resultat += "█";
        }
        for(int i=0; i<(tailleLigne - length(text) - 8)/2; i++){
            resultat += "█";
        }
        resultat += "  ██";

        return resultat;
    }

    String cadre(String text1, String text2, int ecart){
        String resultat = "";
        int tailleLigne = 190;

        resultat += "██  ";
        for(int i=0; i<ecart; i++){
            resultat += "█";
        }
        resultat += text1;
        for(int i=0; i<tailleLigne - length(text1) - length(text2) - 8 - ecart*2; i++){
            resultat += "█";
        }
        resultat += text2;
        for(int i=0; i<ecart; i++){
            resultat += "█";
        }
        resultat += "  ██";

        return resultat;
    }

    String bas(){
        String resultat = "";
        int tailleLigne = 190;

        resultat += "██  ";
        for(int i=0; i<tailleLigne-8; i++){
            resultat += " ";
        }
        resultat += "  ██" + '\n';
        for(int i=0; i<tailleLigne; i++){
            resultat += "█";
        }

        return resultat;
    }

    String arena(){
        String resultat = "";
        int tailleLigne = 190;

        resultat += "██  ████";
        for(int i=0; i<86-pose; i++){
            resultat += "▯";
        }
    
        resultat += "🔴";

        for(int i=0; i<86+pose; i++){
            resultat += "▯";
        }
        resultat += "████  ██";

        return resultat;
    }

    String refreshFPS(int FPS){
        String displayFPS = "";

        if(FPS<10){
            displayFPS = " " + FPS;
        }else if(FPS>9){
            displayFPS = "" + FPS;
        }
        
        return displayFPS;
    }

    String refreshclock(long clock){
        int minute = (int)(clock / 60000);
        int tempsRestant = (int)(clock%60000);
        int seconde = tempsRestant/1000;
        int milliSeconde = tempsRestant%1000;

        String displayMinute = "";
        String displaySeconde = "";
        String displayMilliSeconde = "";

        if(minute<10){
            displayMinute = "0" + minute;
        }else if(minute>9){
            displayMinute = "" + minute;
        }
        if(seconde<10){
            displaySeconde = "0" + seconde;
        }else if(seconde>9){
            displaySeconde = "" + seconde;
        }
        if(milliSeconde>99){
            displayMilliSeconde = substring(""+milliSeconde,0, 2);
        }else if(milliSeconde<10){
            displayMilliSeconde = "0" + milliSeconde;
        }else if(milliSeconde>9){
            displayMilliSeconde = "" + milliSeconde;
        }
        
        return displayMinute + ":" + displaySeconde + ":" + displayMilliSeconde;
    }
// Partie
    String genererHeader(long clock, int FPS){
        String resultat = "";
        
        resultat += plein();
        resultat += cadre(" Clock: " + refreshclock(clock) + " ", " FPS: " + refreshFPS(FPS) + " ", 6);

        return resultat;
    }
// Partie
    String genererMain(){
        String resultat = "";

        resultat += haut();        
        for(int i=0; i<=4; i++){
            resultat += centre("");
        }
        resultat += centre("||");
        resultat += arena();
        resultat += centre("||");
        for(int i=0; i<=4; i++){
            resultat += centre("");
        }
        resultat += bas();

        return resultat;
    }

    String genererFooter(QCM[] tableauQuestion, int question, joueur joueur_1, joueur joueur_2, boolean[] input){
        String resultat = "";
        joueur joueurActuel;
        if(aQuiLeTour % 2 == 0){
            joueurActuel = joueur_1;
        }else{
            joueurActuel = joueur_2;
        }
                
        
        resultat += haut();
        resultat += centre("");
        resultat += centre("[ " + joueurActuel.nom + " - Score: " +  joueurActuel.score + " - Question "+ (joueurActuel.nbQuestion+1) +"/10 theme: " + tableauQuestion[question].theme + " - Difficulté: " + tableauQuestion[question].difficulte + "/10]");
        resultat += cadre(" " + joueur_1.nom + " ", " " +joueur_2.nom + " ", 4);
        resultat += cadre(" SCORE : " + joueur_1.score + " ", " SCORE : " + joueur_2.score + " ", 4);
        resultat += centre(" " + tableauQuestion[question].question + " ");
        resultat += centre("");
        resultat += centre(" F) " + tableauQuestion[question].proposition[0] + " ██████████████ G) " + tableauQuestion[question].proposition[1] + " ");
        resultat += centre(" H) " + tableauQuestion[question].proposition[2] + " ██████████████ J) " + tableauQuestion[question].proposition[3] + " ");
        resultat += centre("La repoonse est " + inputkey[tableauQuestion[question].reponse]);
        resultat += centre("");
        resultat += bas();

        if(!timer(0.3)){
            background("yellow");
        }

        for(int i=0; i<4; i++){
            if(input[i]){
                aRepondu = true;
                timer = getTime();
                if(i == tableauQuestion[question].reponse){
                    background("green");
                    joueurActuel.score += tableauQuestion[question].difficulte*10;
                    if(aQuiLeTour % 2 == 0){
                        pose += 1;
                    }else{
                        pose -= 1;
                    }
                }else{
                    background("red");
                    if(aQuiLeTour % 2 == 0){
                        pose -= 1;
                    }else{
                        pose += 1;
                    }  
                    
                }
                aQuiLeTour += 1; 
            }
        }

        return resultat;
    }
    
    void keyTypedInConsole(int c){
		if (c == 'f' || c == 'F'){
			input[0] = true;
            //println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
		}else if (c == 'g' || c == 'G'){
			input[1] = true;
            //println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
		}else if (c == 'h' || c == 'H'){
			input[2] = true;
            //println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
		}else if (c == 'j' || c == 'J'){
			input[3] = true;
            //println("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");
        }

    }

    boolean timer(double seconde){
        boolean issue = false;
        long now = getTime();
        if(now - timer < seconde*1000){
            issue = true;
        }
        return issue;
    }

    void algorithm(){
        enableKeyTypedInConsole(false);
        QCM[] tableauQuestion = genererTableauQuestion(); // On genere un tableau de question a partire d'un fichier csv dans un but d'optimisation.
        boolean enJeu = true;

        joueur joueur_1 = genererJoueur(); // 1 joueur pour la version beta, on prevoit des autres joueurs "joueur2" et "cpu".
        joueur joueur_2 = genererJoueur(); // 1 joueur pour la version beta, on prevoit des autres joueurs "joueur2" et "cpu".
        joueur_1.nom = "Player_1";
        joueur_2.nom = "Player_2";

        int question = (int)(random()*length(tableauQuestion)-1)+1;

        int frame = 0;
        int fps = 15;
        int realFPS = fps;

        long clock = getTime();
        long fixedclock = getTime();
        long fpsConter = getTime();
        int frameCounter = 0;
        background("yellow");

        String header = "";
        String main = "";
        String footer = "";


        do{
           	enableKeyTypedInConsole(true); 

            bonneReponse = false;
            aRepondu = false;
            input = new boolean[] {false, false, false, false};

            clock = getTime();
            if(clock-fpsConter>=1000){
                realFPS = (frame - frameCounter);
                frameCounter = frame;
                fpsConter = getTime();
            }
            frame ++;
            
            //FPS + Info
            delay(1000/fps);

            header = genererHeader(clock-fixedclock, realFPS);
            main = genererMain();
            footer = genererFooter(tableauQuestion, question, joueur_1, joueur_2, input);

            if (aRepondu==true){
                question = (int)(random()*length(tableauQuestion)-1)+1;
            }

            enableKeyTypedInConsole(false);
            
            println();
            println(header);
            println(main);
            println(footer);

            
        }while(true);
    }
}
