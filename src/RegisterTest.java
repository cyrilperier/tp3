import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stev.kwikemart.Item;
import stev.kwikemart.PaperRoll;
import stev.kwikemart.Register;
import stev.kwikemart.Upc;

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
    private void verificationPrix_intervalleMaxEtMin_prixCorrecte(){

        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", 1, 1.5));
        grocery.add(new Item(Upc.generateCode("22804918500"), "Beef", 0.5, 5.75));
        // Oops, we remove the bananas
        grocery.add(new Item(Upc.generateCode("12345678901"), "Bananas", -1, 1.5));
        grocery.add(new Item(Upc.generateCode("64748119599"), "Chewing gum", 2, 0.99));
        grocery.add(new Item(Upc.generateCode("44348225996"), "Gobstoppers", 1, 0.99));
        grocery.add(new Item(Upc.generateCode("34323432343"), "Nerds", 2, 1.44));
        grocery.add(new Item(Upc.generateCode("54323432343"), "Doritos Club", 1, 0.5));
        grocery.add(new Item(Upc.generateCode("61519314159"), "Doritos", 1, 1.25));

        // Print receipt
        System.out.println(register.print(grocery));



    }


    @Test
    private void deuxMemeCUP_QuantitePositive_Invalid(){
    }

}