package de.oschoen.bdd.doc;

import java.util.ArrayList;
import java.util.List;


public class TestObject {


    private final String simpleName;
    private final String packageName;

    private final List<Scenario> scenarios = new ArrayList<Scenario>();

    public TestObject(String packageName, String simpleName) {
        this.packageName = packageName;
        this.simpleName = simpleName;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return packageName + "." + simpleName;
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public void addScenario(Scenario scenario) {
        scenarios.add(scenario);
    }

    @Override
    public String toString() {
        return "TestObject{" +
                "name='" + getName() + '\'' +
                ", scenarios=" + scenarios +
                '}';
    }

    public static List<TestObject> getAllTestObjects(ClassHierarchy classHierarchy) {

        List<TestObject> testObjects = new ArrayList<TestObject>();
        List<JavaClass> allNoAbstractClasses = classHierarchy.getAllNoAbstractClasses();

        for (JavaClass clazz : allNoAbstractClasses) {
            List<TestMethod> testMethods = classHierarchy.getTestMethodsForClass(clazz.getName());

            if (testMethods.size() > 0) {
                TestObject testObject = new TestObject(clazz.getPackageName(), clazz.getSimpleName());
                for (TestMethod testMethod : testMethods) {
                    testObject.addScenario(testMethod.getScenario());
                }
                testObjects.add(testObject);
            }
        }
        return testObjects;
    }
}
