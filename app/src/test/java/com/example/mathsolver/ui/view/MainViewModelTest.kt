package com.example.mathsolver.ui.view
import com.example.mathsolver.data.database.MathExpressionHistoryEntity
import com.example.mathsolver.data.repository.MathExpressionRepository
import com.example.mathsolver.ui.view.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    // This scope is used to launch and control coroutines during testing
    private val testScope = TestCoroutineScope(testDispatcher)

    // The view model to be tested
    private lateinit var viewModel: MainViewModel

    // Mock repository
    private val repository = mockk<MathExpressionRepository>()

    @Before
    fun setup() {
        viewModel = MainViewModel(repository)
    }

    @After
    fun cleanup() {
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `evaluateExpression success`() {
        // Mock data
        val expression = "2 + 2"
        val expectedResult = "4"
        val mockEntity = MathExpressionHistoryEntity(expression = expression, result = expectedResult)

        // Mock the repository's behavior
        coEvery { repository.evaluateExpression(expression) } returns expectedResult
        coEvery { repository.insertExpression(any()) } returns Unit

        // Call the function to be tested
        testScope.runBlockingTest {
            viewModel.evaluateExpression(expression)
        }

        // Verify that the result matches the expected result
        val result = viewModel.expressionResult.value
        assert(result != null)
        assert(result?.expression == expression)
        assert(result?.result == expectedResult)
    }

    @Test
    fun `evaluateExpression failure`() {
        // Mock data
        val expression = "invalid expression"
        val errorMessage = "Invalid expression format"

        // Mock the repository's behavior to throw an exception
        coEvery { repository.evaluateExpression(expression) } throws Exception(errorMessage)

        // Call the function to be tested
        testScope.runBlockingTest {
            viewModel.evaluateExpression(expression)
        }

        // Verify that the result is an error message
        val result = viewModel.expressionResult.value
        assert(result != null)
        assert(result?.expression == null)
        assert(result?.result == "Error: $errorMessage")
    }
}
