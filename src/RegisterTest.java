import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import stev.kwikemart.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Project: tp3
 * Package: PACKAGE_NAME
 * Participant: Rémy Launois, Carla Faboumy, Cyril Perier
 * Code : LAUR08069900, PERC12109605, FABC20539901
 * Classe d'équivalence :
 * ------------------------------------------------------------------------
 * Prix :
 * ------------------------------------------------------------------------
 * Valide                                       Non Valide
 * ------------------------------------------------------------------------
 * V1 : article unitaire >= 0 $ total           i1 : article unitaire < 0 $
 * V2: article unitaire <= 35 $                 i2 : article unitaire >= 35 $
 * V3 : prix fractionnaire valide               i3 : Prix fractionnaire non valide
 * V16: prix total >=0$                         i17: prix total<0$
 *------------------------------------------------------------------------
 * Liste de produit :
 * ------------------------------------------------------------------------
 * Valide                                       Non Valide
 * ------------------------------------------------------------------------
 * V4 : <= 1 article                            i4 : < 1 article
 * V5 : >= 10  articles                         i5 : > 10  articles
 * ------------------------------------------------------------------------
 * CUP
 * ------------------------------------------------------------------------
 * Valide                                       Non Valide
 *  * ------------------------------------------------------------------------
 * V6 : 12 caractères                           i6 : < 12 caractères
 * V7 : 12e chiffre respecte la règle           i7 : > 12 caractères
 * V8 : 2 x même CUP -> 1 valeur nég et         i8 : 12eme chiffre ne respecte pas la règle
 *      une valeur positive
 * V9 : CUP 5*                                  i9: Deux même CUP avec quantité positive
 * V10 : CUP 2*
 * ------------------------------------------------------------------------
 * Rabais :
 * ------------------------------------------------------------------------
 * Valide                                       Non Valide
 * ------------------------------------------------------------------------
 * V11 : Nb articles >= 5                       i10 : < 5 Nb articles
 * V12: Prix tot >= 2$                          i11 : < 2$ Prix total
 * ------------------------------------------------------------------------
 * Coupon :
 * ------------------------------------------------------------------------
 * Valide                                       Non Valide
 * ------------------------------------------------------------------------
 * V13 : 1 coupon / CUP                         i14 : Deux coupons avec le même CUP
 * V14 : Val coupon < Prix tot                  i15 : valeur coupon > prix total
 * V15 : Val coupon > 0                         i16 : valeur coupon < 0
 *------------------------------------------------------------------------
 * TESTS COMBINES:
 *
 * TEST      PRIX            LISTE DE PRODUIT        CUP         RABAIS          COUPON          RÉSULTAT            HEURISTIQUE UTILISEE
 *  1        V1 ∩ V2 ∩ v16                                                                       PRIX CORRECT        GROUPE ET INTERVALLE
 *  2        V3                                      V10                                         PRIX CORRECT        SPECIFIQUE
 *  3                        V4 ∩ V5                                                             LIST VALIDE         INTERVALLE
 *  4                                                V6 ∩ V7                                     CUP VALIDE          SPECIFIQUE
 *  5                                                V8                                          CUP VALIDE
 *  6                                                V9 ∩ V10                                    CUP VALIDE          GROUPE
 *  7                                                           V11 ∩ V12                        RABAIS VALIDE       GROUPE ET INTERVALLE
 *  8                                                V9                          V13 ∩ V14 ∩ V15 COUPON VALIDE       GROUPE
 *  9        I1 ∪ I2                                                                             MSG ERREUR          GROUPE
 *  10       I3                                      I18                                         MSG ERREUR          GROUPE
 *  11                       I4 ∪ I5                                                             MSG ERREUR          INTERVALLE
 *  12                                               I6 ∪ I7                                     MSG ERREUR          INTERVALLE
 *  13                                               I8                                          MSG ERREUR          SPECIFIQUE
 *  14                                               I9 ∪ I14                                    MSG ERREUR          GROUPE
 *  15                                                                           I15 ∪ I16       Coupon suivant      GROUPE ET INTERVALLE
 *  16                                                           V11 ∩ I11                       Pas de rabais       GROUPE
 *  17                                                           V12 ∩ I10                       Pas de rabais       GROUPE
 */
public class RegisterTest {

    private Register register;
    private List<Item> grocery;


    @Before
    public void setUp() throws Exception {

        this.register = Register.getRegister();
        register.changePaper(PaperRoll.LARGE_ROLL);

        this.grocery = new ArrayList<>();

    }

    @Test
    public void un_VerificationPrix_IntervalleMaxEtMinPourUnarticleEtPrixTotalNonNegatif_PrixCorrecte(){

        List<Item> grocery = new ArrayList<Item>();

        grocery.add(new Item(Upc.generateCode("64748119599"), "Chewing gum", 1, 0.99));

        // Print receipt
        System.out.println(register.print(grocery));

    }

    @Test
    public void deux_VerificationPrix_PrixFractionaireAvecCUPCommencantParDeux_PrixCorrecte(){

        List<Item> grocery = new ArrayList<Item>();

        grocery.add(new Item(Upc.generateCode("22804918500"), "Beef", 0.5, 5.75));

        // Print receipt
        System.out.println(register.print(grocery));

    }

    @Test
    public void trois_VerificationList_BonNombreArticle_ListValide(){

        List<Item> grocery = new ArrayList<Item>();

        grocery.add(new Item(Upc.generateCode("22804918500"), "Beef", 0.5, 5.75));

        // Print receipt
        System.out.println(register.print(grocery));

    }

    @Test
    public void quatre_VerificationCUP_BonNombreDeCaractereEtDouziemeRespecteLaRegle_CUPValide(){

        List<Item> grocery = new ArrayList<Item>();

        grocery.add(new Item("647481195995", "Chewing gum", 1, 0.99));

        // Print receipt
        System.out.println(register.print(grocery));

    }

    @Test
    public void cinq_VerificationCUP_DeuxProduitAvecMemeCUPMaisUnQuantiteNegative_CUPValide(){

        List<Item> grocery = new ArrayList<Item>();
        grocery.add(new Item(Upc.generateCode("64748119599"), "Chewing gum", 2, 0.99));
        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 1, 1.5));

        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", -1, 1.5));

        System.out.println(register.print(grocery));

    }

    @Test
    public void six_VerificationCUP_CupCommenceParLeBonChiffreParRapportAuProduit_CUPValide(){

        List<Item> grocery = new ArrayList<Item>();
        grocery.add(new Item(Upc.generateCode("22804918500"), "Beef", 0.5, 5.75));
        grocery.add(new Item(Upc.generateCode("54323432343"), "Doritos Club", 1, 0.5));
        grocery.add(new Item(Upc.generateCode("61519314159"), "Doritos", 1, 1.25));

        System.out.println(register.print(grocery));

    }

/**
 * TEST      PRIX            LISTE DE PRODUIT        CUP         RABAIS          COUPON          RÉSULTAT            HEURISTIQUE UTILISEE
 *  7                                                            V11 ∩ V12                        RABAIS VALIDE       GROUPE ET INTERVALLE
 */
    @Test
    public void sept_ApplicationDeRabais_5Articles_PrixTotalSuperieurA2_RabaisValide() {

        List<Item> grocery = new ArrayList<Item>();
        grocery.add(new Item(Upc.generateCode("12345678901"), "Newspappers", 1, 0.20));
        grocery.add(new Item(Upc.generateCode("64748119599"), "Chewing gum", 2, 0.30));
        grocery.add(new Item(Upc.generateCode("44348225996"), "Gobstoppers", 1, 0.40));
        grocery.add(new Item(Upc.generateCode("34323432343"), "Nerds", 1, 0.35));
        grocery.add(new Item(Upc.generateCode("61519314159"), "Doritos", 1, 1.25));

        assertEquals(true,register.print(grocery).contains("Rebate for 5 items"));

        System.out.println(register.print(grocery));
    }
/**
 * TEST      PRIX            LISTE DE PRODUIT        CUP         RABAIS          COUPON          RÉSULTAT            HEURISTIQUE UTILISEE
 *  8                                                 V9                          V13 ∩ V14 ∩ V15 COUPON VALIDE       GROUPE
 */
    @Test
    public void huit_ApplicationDeCoupon_PrixCouponInferieurPrixTotalEtSuperieurAZero_CouponValide() {
        Item coupon = new Item("", "", 0, 0);

        List<Item> grocery = new ArrayList<Item>();
        grocery.add(new Item(Upc.generateCode("61519314159"), "Doritos", 1, 1.25));
        grocery.add(new Item(Upc.generateCode("54323432343"), "Doritos Club", 1, 0.5));

        for (Item item : grocery) {
            if (item.getUpc().startsWith("5")) coupon = item;
        }

        assertEquals(true,register.print(grocery).contains("Coupon: " + coupon.getDescription()));

        System.out.println(register.print(grocery));
    }

    /**
     * TEST      PRIX            LISTE DE PRODUIT        CUP         RABAIS          COUPON          RÉSULTAT            HEURISTIQUE UTILISEE
     *  9        I1 ∪ I2                                                                             MSG ERREUR          GROUPE
     */
    @Test(expected = AmountException.class)
    public void neuf_PrixArticle_prixInferieurAZero_AmountException() {

        List<Item> grocery = new ArrayList<Item>();
        grocery.add(new Item(Upc.generateCode("12345678901"), "Newspappers", 1, -1));

        System.out.println(register.print(grocery));
    }

    /**
     * TEST      PRIX            LISTE DE PRODUIT        CUP         RABAIS          COUPON          RÉSULTAT            HEURISTIQUE UTILISEE
     *  10       I3                                      I18                                         MSG ERREUR          GROUPE
     */
    @Test(expected = InvalidQuantityException.class)
    public void dix_PrixFractionnaireArticle_prixNonValide_AmountException() {

        List<Item> grocery = new ArrayList<Item>();
        grocery.add(new Item(Upc.generateCode("62804918500"), "Beef", 0.5, 5.75));

        System.out.println(register.print(grocery));
    }

    /**
     * TEST      PRIX            LISTE DE PRODUIT        CUP         RABAIS          COUPON          RÉSULTAT            HEURISTIQUE UTILISEE
     *  11                       I4 ∪ I5                                                             MSG ERREUR          INTERVALLE
     */
    @Test(expected = RegisterException.class)
    public void onze_ListeArticles_nombreArticlesNonValide_RegisterException() {

        List<Item> grocery = new ArrayList<Item>();

        System.out.println(register.print(grocery));
    }

    /**
     * TEST      PRIX            LISTE DE PRODUIT        CUP         RABAIS          COUPON          RÉSULTAT            HEURISTIQUE UTILISEE
     *  12                                               I6 ∪ I7                                     MSG ERREUR          INTERVALLE
     */
    @Test(expected = InvalidUpcException.class)
    public void douze_ListeArticles_CUPInvalide_RegisterException() {

        List<Item> grocery = new ArrayList<Item>();
        grocery.add(new Item("12345678901", "Newspappers", 1, 0));

        System.out.println(register.print(grocery));
    }

    /**
     * TEST      PRIX            LISTE DE PRODUIT        CUP         RABAIS          COUPON          RÉSULTAT            HEURISTIQUE UTILISEE
     *  13                                               I8                                          MSG ERREUR          SPECIFIQUE
     */
    @Test(expected = InvalidUpcException.InvalidCheckDigitException.class)
    public void treize_CheckDigit_LastNumber_invalid() {
        grocery.add(new Item("036000291451", "Bananas", 1, 1.5));
        System.out.println(register.print(grocery));
    }

    /**
     * TEST      PRIX            LISTE DE PRODUIT        CUP         RABAIS          COUPON          RÉSULTAT            HEURISTIQUE UTILISEE
     *  14                                               I9 ∪ I14                                    MSG ERREUR          GROUPE
     */
    @Test(expected = Register.DuplicateItemException.class)
    public void quatorze_DeuxMemesCUP_QuantitePositive_Invalid() {
        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 1, 1.5));
        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 1, 1.5));
        System.out.println(register.print(grocery));
    }

    /**
     * TEST      PRIX            LISTE DE PRODUIT        CUP         RABAIS          COUPON          RÉSULTAT            HEURISTIQUE UTILISEE
     *  15                                                                           I15 ∪ I16       Coupon suivant      GROUPE ET INTERVALLE
     */
    @Test()
    public void quinze_ValeurCoupon_SuperieurPrixTotOuInf0_DeuxiemeCoupon() {
        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 2, 0.5));
        grocery.add(new Item(Upc.generateCode("12345677901"), "Apple", 1, 1.5));
        grocery.add(new Item(Upc.generateCode("54323432343"), "Rabais Fruits", 1, 3));
        grocery.add(new Item(Upc.generateCode("54323432443"), "Rabais 1$", 1, 2));
        Assert.assertTrue(register.print(grocery).contains("Rabais 1$"));
    }

    /**
     * TEST      PRIX            LISTE DE PRODUIT        CUP         RABAIS          COUPON          RÉSULTAT            HEURISTIQUE UTILISEE
     *  16                                                           V11 ∩ I11                       Pas de rabais       GROUPE
     */
    @Test()
    public void seize_VerifArticle_Sup5EtPrixInf2_PasDeRabais() {
        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 2, 0.5));
        grocery.add(new Item(Upc.generateCode("12345677901"), "Apple", 1, 0.2));
        grocery.add(new Item(Upc.generateCode("12345577901"), "Green-Apple", 1, 0.5));
        grocery.add(new Item(Upc.generateCode("12345476901"), "Apple-juice", 1, 0.1));
        grocery.add(new Item(Upc.generateCode("12344477901"), "Oranges", 1, 0.0));
        Assert.assertFalse(register.print(grocery).contains("Rebate for 5 items  "));
    }

    /**
     * TEST      PRIX            LISTE DE PRODUIT        CUP         RABAIS          COUPON          RÉSULTAT            HEURISTIQUE UTILISEE
     *  17                                                           V12 ∩ I10                       Pas de rabais       GROUPE
     */
    @Test()
    public void dix_sept_VerifArticle_Inf5EtPrixSup2_PasDeRabais() {
        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 2, 0.5));
        grocery.add(new Item(Upc.generateCode("12345677901"), "Apple", 1, 0.5));
        grocery.add(new Item(Upc.generateCode("12345577901"), "Green-Apple", 1, 0.5));
        grocery.add(new Item(Upc.generateCode("12345476901"), "Apple-juice", 1, 1.1));
        Assert.assertFalse(register.print(grocery).contains("Rebate for 5 items  "));
    }

    @After
    public void tearDown() throws Exception {
    }

}