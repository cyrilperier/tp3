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
    public void verificationPrix_IntervalleMaxEtMin_PrixCorrecte(){

        List<Item> grocery = new ArrayList<Item>();

        grocery.add(new Item(Upc.generateCode("64748119599"), "Chewing gum", 2, 0.99));

        // Print receipt
        System.out.println(register.print(grocery));

    }

    @Test
    public void verificationPrix_PrixFractionaireAvecCUPCommencantParDeux_PrixCorrecte(){

        List<Item> grocery = new ArrayList<Item>();

        grocery.add(new Item(Upc.generateCode("22804918500"), "Beef", 0.5, 5.75));

        // Print receipt
        System.out.println(register.print(grocery));

    }

    @Test
    public void verificationList_BonNombreArticle_ListValide(){

        List<Item> grocery = new ArrayList<Item>();

        grocery.add(new Item(Upc.generateCode("22804918500"), "Beef", 0.5, 5.75));

        // Print receipt
        System.out.println(register.print(grocery));

    }


    @Test(expected = InvalidUpcException.InvalidCheckDigitException.class)
    public void quatorze_checkDigit_LastNumber_invalid() {
        grocery.add(new Item("036000291451", "Bananas", 1, 1.5));
        System.out.println(register.print(grocery));
    }
    @Test(expected = Register.DuplicateItemException.class)
    public void quinze_deuxMemeCUP_QuantitePositive_Invalid() {
        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 1, 1.5));
        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 1, 1.5));
        System.out.println(register.print(grocery));
    }

    @Test()
    public void seize_valeurCoupon_SuperieurPrixTotOuInf0_DeuxiemeCoupon() {
        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 2, 0.5));
        grocery.add(new Item(Upc.generateCode("12345677901"), "Apple", 1, 1.5));
        grocery.add(new Item(Upc.generateCode("54323432343"), "Rabais Fruits", 1, 3));
        grocery.add(new Item(Upc.generateCode("54323432443"), "Rabais 1$", 1, 2));
        Assert.assertTrue(register.print(grocery).contains("Rabais 1$"));
    }

    @Test()
    public void dix_sept_verifArticle_Sup5EtPrixInf2_PasDeRabais() {
        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 2, 0.5));
        grocery.add(new Item(Upc.generateCode("12345677901"), "Apple", 1, 0.2));
        grocery.add(new Item(Upc.generateCode("12345577901"), "Green-Apple", 1, 0.5));
        grocery.add(new Item(Upc.generateCode("12345476901"), "Apple-juice", 1, 0.1));
        grocery.add(new Item(Upc.generateCode("12344477901"), "Oranges", 1, 0.0));
        Assert.assertFalse(register.print(grocery).contains("Rebate for 5 items  "));
    }

    @Test()
    public void dix_huit_verifArticle_Inf5EtPrixSup2_PasDeRabais() {
        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 2, 0.5));
        grocery.add(new Item(Upc.generateCode("12345677901"), "Apple", 1, 0.5));
        grocery.add(new Item(Upc.generateCode("12345577901"), "Green-Apple", 1, 0.5));
        grocery.add(new Item(Upc.generateCode("12345476901"), "Apple-juice", 1, 1.1));
        Assert.assertFalse(register.print(grocery).contains("Rebate for 5 items  "));
    }


}