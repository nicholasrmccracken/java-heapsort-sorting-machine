import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Nicholas McCracken and Jack Mikesell
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Test cases for constructors;
     */

    /**
     * Test constructor with string comparator ORDER.
     */
    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    /*
     * Test cases for kernel methods.
     */

    /**
     * Test add by adding one element added to an empty sorting machine.
     */
    @Test
    public final void testAddOneToEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    /**
     * Test add by adding many elements added to an empty sorting machine.
     */
    @Test
    public final void testAddManyToEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "blue", "red");
        m.add("green");
        m.add("blue");
        m.add("red");
        assertEquals(mExpected, m);
    }

    /**
     * Test add by adding one element added to a nonempty sorting machine.
     */
    @Test
    public final void testAddOneToNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "yellow", "purple", "brown");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "yellow", "purple", "brown");
        m.add("green");
        assertEquals(mExpected, m);
    }

    /**
     * Test add by adding many elements added to a nonempty sorting machine.
     */
    @Test
    public final void testAddManyToNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "yellow", "purple", "brown");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "blue", "red", "yellow", "purple", "brown");
        m.add("green");
        m.add("blue");
        m.add("red");
        assertEquals(mExpected, m);
    }

    /**
     * Test changeToExtracionMode by calling method on an empty sorting machine.
     */
    @Test
    public final void testChangeToExtractionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);

        m.changeToExtractionMode();

        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }

    /**
     * Test changeToExtracionMode by calling method on a sorting machine with a
     * single element.
     */
    @Test
    public final void testChangeToExtractionModeSingle() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "red");

        m.changeToExtractionMode();

        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }

    /**
     * Test changeToExtracionMode by calling method on a sorting machine with a
     * multiple elements.
     */
    @Test
    public final void testChangeToExtractionModeMany() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "blue", "red", "yellow", "purple", "brown");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "blue", "red", "yellow", "purple", "brown");

        m.changeToExtractionMode();

        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected, m);
    }

    /**
     * Test removeFirst by removing one element to make a nonempty sorting
     * machine empty.
     */
    @Test
    public final void testRemoveFirstOneToEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green");

        String element = m.removeFirst();
        String elementExpected = mExpected.removeFirst();

        assertEquals(elementExpected, element);
        assertEquals(mExpected, m);
    }

    /**
     * Test removeFirst by removing all elements in a nonempty sorting machine.
     */
    @Test
    public final void testRemoveFirstManyToEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red", "blue");

        String element1 = m.removeFirst();
        String elementExpected1 = mExpected.removeFirst();
        String element2 = m.removeFirst();
        String elementExpected2 = mExpected.removeFirst();
        String element3 = m.removeFirst();
        String elementExpected3 = mExpected.removeFirst();

        assertEquals(elementExpected1, element1);
        assertEquals(elementExpected2, element2);
        assertEquals(elementExpected3, element3);
        assertEquals(mExpected, m);
    }

    /**
     * Test removeFirst by removing one element from a nonempty sorting machine.
     */
    @Test
    public final void testRemoveFirstOneToNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "blue", "red", "yellow", "purple", "brown");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "blue", "red", "yellow", "purple", "brown");

        String element = m.removeFirst();
        String elementExpected = mExpected.removeFirst();

        assertEquals(elementExpected, element);
        assertEquals(mExpected, m);
    }

    /**
     * Test removeFirst by removing many elements in a nonempty sorting machine.
     */
    @Test
    public final void testRemoveFirstManyToNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "blue", "red", "yellow", "purple", "brown");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "blue", "red", "yellow", "purple", "brown");

        String element1 = m.removeFirst();
        String elementExpected1 = mExpected.removeFirst();
        String element2 = m.removeFirst();
        String elementExpected2 = mExpected.removeFirst();
        String element3 = m.removeFirst();
        String elementExpected3 = mExpected.removeFirst();

        assertEquals(elementExpected1, element1);
        assertEquals(elementExpected2, element2);
        assertEquals(elementExpected3, element3);
        assertEquals(mExpected, m);
    }

    /**
     * Test removeFirst by removing many elements in a nonempty sorting machine.
     */
    @Test
    public final void testRemoveFirstManyComplete() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "blue", "red", "yellow", "purple", "brown", "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "blue", "red", "yellow", "purple", "brown", "green");

        String element1 = m.removeFirst();
        String elementExpected1 = mExpected.removeFirst();
        String element2 = m.removeFirst();
        String elementExpected2 = mExpected.removeFirst();
        String element3 = m.removeFirst();
        String elementExpected3 = mExpected.removeFirst();

        assertEquals(elementExpected1, element1);
        assertEquals(elementExpected2, element2);
        assertEquals(elementExpected3, element3);
        assertEquals(mExpected, m);
    }

    /**
     * Test isInsertionMode when insertionMode is initialized as true.
     */
    @Test
    public final void testIsInsertionModeTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);

        assertTrue(m.isInInsertionMode());
    }

    /**
     * Test isInsertionMode when insertionMode is initialized as false.
     */
    @Test
    public final void testIsInsertionModeFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);

        assertTrue(!m.isInInsertionMode());
    }

    /**
     * Test isInsertionMode when insertionMode is changed via
     * changeToExtractionMode.
     */
    @Test
    public final void testIsInsertionModePostChange() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);

        m.changeToExtractionMode();

        assertTrue(!m.isInInsertionMode());
    }

    /**
     * Test order when machine order is initialized to ORDER.
     */
    @Test
    public final void testOrder() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);

        assertEquals(ORDER, m.order());
    }

    /**
     * Test size when sorting machine is empty.
     */
    @Test
    public final void testSizeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);

        assertEquals(0, m.size());
    }

    /**
     * Test size when sorting machine has one element.
     */
    @Test
    public final void testSizeOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red");

        assertEquals(1, m.size());
    }

    /**
     * Test size when sorting machine has many elements.
     */
    @Test
    public final void testSizeMany() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "blue", "red", "yellow", "purple", "brown");

        assertEquals(6, m.size());
    }

    /**
     * Test size after adding an element to sorting machine.
     */
    @Test
    public final void testSizePostAdd() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "green");

        m.add("red");

        assertEquals(2, m.size());
    }

    /**
     * Test size after removing an element from sorting machine.
     */
    @Test
    public final void testSizePostRemove() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green");

        m.removeFirst();

        assertEquals(0, m.size());
    }

}
