package spaceInvaders;

public interface Nastavitve {
	//Splosne nastavitve 
    int premikY = 16;
    int steviloVesoljckov = 50; //Zgolj sprememba stevila vesoljckov tukaj ni dovolj. Treba je narediti spremembo se v igraInit, kjer dodamo ustrezno stevilo vesoljckov
    int zamik = 17; //Daljsi zamik, pocasneje bo igra tekla
    int visinaIgralca = 20;
    int sirinaOkna = 600;
    int visinaOkna = 700;
    int vesoljcekX = 150; //Zacetna lokacija vesoljcka
    int vesoljcekY = 10;
}
