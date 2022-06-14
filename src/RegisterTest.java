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
 *Prix :
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
 *TEST COMBINE:
 *
 *TEST      PRIX            LISTE DE PRODUIT        CUP         RABAIS          COUPON          RÉSULTAT            HEURISTIQUE UTILISE
 * 1        V1 ∩ V2 ∩ v16                                                                       PRIX CORRECT        GROUPE ET INTERVALLE
 * 2        V3                                      V10                                         PRIX CORRECT        SPECIFIQUE
 * 3                        V4 ∩ V5                                                             LIST VALIDE         INTERVALLE
 * 4                                                V6 ∩ V7                                     CUP VALIDE          SPECIFIQUE
 * 5                                                V8                                          CUP VALIDE
 * 6                                                V9 ∩ V10                                    CUP VALIDE          GROUPE
 * 7                                                           V11 ∩ V12                        RABAIS VALIDE       GROUPE ET INTERVALLE
 * 8                                                V9                          V13 ∩ V14 ∩ V15 COUPON VALIDE       GROUPE
 * 9        I1 ∪ I2                                                                             MSG ERREUR          GROUPE
 * 10       I3                                      I18                                         MSG ERREUR          GROUPE
 * 11                       I4 ∪ I5                                                             MSG ERREUR          INTERVALLE
 * 12                                               I6 ∪ I7                                     MSG ERREUR          INTERVALLE
 * 13                                               I8                                          MSG ERREUR          SPECIFIQUE
 * 14                                               I9 ∪ I14                                    MSG ERREUR          GROUPE
 * 15                                                                           I15 ∪ I16       MSG ERREUR          GROUPE ET INTERVALLE
 * 16                                                           V11 ∩ I11                       MSG ERREUR          GROUPE
 * 17                                                           V12 ∩ I10                       MSG ERREUR          GROUPE
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

    @After
    public void tearDown() throws Exception {
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


    @Test(expected = InvalidUpcException.InvalidCheckDigitException.class)
    public void treize_checkDigit_LastNumber_invalid() {
        grocery.add(new Item("036000291451", "Bananas", 1, 1.5));
        System.out.println(register.print(grocery));
    }
    @Test(expected = Register.DuplicateItemException.class)
    public void quatorze_deuxMemeCUP_QuantitePositive_Invalid() {
        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 1, 1.5));
        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 1, 1.5));
        System.out.println(register.print(grocery));
    }

    @Test()
    public void quinze_valeurCoupon_SuperieurPrixTotOuInf0_DeuxiemeCoupon() {
        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 2, 0.5));
        grocery.add(new Item(Upc.generateCode("12345677901"), "Apple", 1, 1.5));
        grocery.add(new Item(Upc.generateCode("54323432343"), "Rabais Fruits", 1, 3));
        grocery.add(new Item(Upc.generateCode("54323432443"), "Rabais 1$", 1, 2));
        Assert.assertTrue(register.print(grocery).contains("Rabais 1$"));
    }

    @Test()
    public void seize_verifArticle_Sup5EtPrixInf2_PasDeRabais() {
        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 2, 0.5));
        grocery.add(new Item(Upc.generateCode("12345677901"), "Apple", 1, 0.2));
        grocery.add(new Item(Upc.generateCode("12345577901"), "Green-Apple", 1, 0.5));
        grocery.add(new Item(Upc.generateCode("12345476901"), "Apple-juice", 1, 0.1));
        grocery.add(new Item(Upc.generateCode("12344477901"), "Oranges", 1, 0.0));
        Assert.assertFalse(register.print(grocery).contains("Rebate for 5 items  "));
    }

    @Test()
    public void dix_sept_verifArticle_Inf5EtPrixSup2_PasDeRabais() {
        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 2, 0.5));
        grocery.add(new Item(Upc.generateCode("12345677901"), "Apple", 1, 0.5));
        grocery.add(new Item(Upc.generateCode("12345577901"), "Green-Apple", 1, 0.5));
        grocery.add(new Item(Upc.generateCode("12345476901"), "Apple-juice", 1, 1.1));
        Assert.assertFalse(register.print(grocery).contains("Rebate for 5 items  "));
    }


}