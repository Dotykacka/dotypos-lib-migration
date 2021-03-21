import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CollectionValidationTest {

}

private class Wrapper<T>(val value: T)