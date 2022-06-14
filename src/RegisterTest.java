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
    @Before
    public void setUp() throws Exception {

        this.register = Register.getRegister();
        register.changePaper(PaperRoll.LARGE_ROLL);

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


}