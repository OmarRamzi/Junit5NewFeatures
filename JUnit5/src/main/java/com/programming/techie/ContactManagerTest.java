package com.programming.techie;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//enables us to ask JUnit to create only one instance of the test class and reuse it between tests.
class ContactManagerTest {
    ContactManager contactManager;
    @BeforeAll
    void setupAll(){
        System.out.println("displayed before doing any tests in this test class");
    }
    @BeforeEach
    void crateUserObject(){
         contactManager= new ContactManager();
    }

    @Test
    @DisplayName("Get a user")
    void createUser(){
        contactManager.addContact("omar","ramzi","01009729129");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    @Test
    @DisplayName("phone number must be 11 digits")
    public void phoneValidation() throws RuntimeException{
        contactManager.addContact("omar", "ramzi", "01009729129");
    }

    @Test
    @DisplayName("first name must be a value")
    public void firstNameValidation() throws RuntimeException{
        contactManager.addContact("omar", "ramzi", "01009729129");
    }

    @AfterEach
    public void teardwon(){
        System.out.println("displayed after each test ");
    }

    @AfterAll
    public void teardwonAll(){
        System.out.println("displayed after all tests ");
    }

    @Test
    void testOnDev()
    {
        System.setProperty("ENV", "DEV");
        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
        //remainder of test will proceed
    }

    @Test
    void testOnProd()
    {
        System.setProperty("ENV", "PROD");
        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
        //remainder of test will be aborted
    }

    //I didn't put it on the nested because nested cannot include static members
    @DisplayName("Parameterized Test way 2")
    @ParameterizedTest
    @MethodSource("phoneList")
    void SecondParametrizedUsers(String input){
        contactManager.addContact("omar","ramzi", input);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }
    private static List<String> phoneList(){
        return Arrays.asList("01009729129", "012787", "01062556522");
    }



    @Test
    @RepeatedTest(value = 7)
    void createSevenUsers(){
        contactManager.addContact("omar","ramzi","01009729129");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }
    @Nested
    class OSvalidations{
        @Test
        @DisplayName("prevent a WINDOWS User")
        @DisabledOnOs(value = OS.WINDOWS, disabledReason = "mac needs this additional test")
        void createMacUser(){
            contactManager.addContact("omar","ramzi","01009729129");
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }

        @Test
        @DisplayName("prevent a MAC User")
        @DisabledOnOs(value = OS.MAC, disabledReason = "mac needs this additional test")
        void createWindowsUser(){
            contactManager.addContact("omar","ramzi","01009729129");
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }
        @Test
        @DisplayName("LINUX User ONLY")
        @EnabledOnOs(value = OS.LINUX, disabledReason = "mac needs this additional test")
        void createLinuxUser(){
            contactManager.addContact("omar","ramzi","01009729129");
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }
        //IN ASSUMPTIONS IN CASE THE assumeTrue() returns false it will not fail but just aborted

    }


    @Nested
    class AllParameterizedTest{

        @DisplayName("Parameterized Test way 1")
        @ParameterizedTest
        @ValueSource(strings = {"01009729129", "012787", "01062556522"})
        void firstParametrizedUsers(String input){
            contactManager.addContact("omar","ramzi", input);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }

        @DisplayName("Parameterized Test way 3 - READ CSV FROM ANOTHER FILE")
        @ParameterizedTest
        @CsvFileSource(resources = "/phoneNumbers.CSV")
        void ThirdParametrizedUsers(String input){
            contactManager.addContact("omar","ramzi", input);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }

    }


}