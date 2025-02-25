
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.rickyandmortyapp.ui.view.CharacterDetailsScreen
import com.example.rickyandmortyapp.ui.view.CharacterItem
import com.example.rickyandmortyapp.ui.viewmodel.CharactersViewModel
import org.koin.androidx.compose.koinViewModel

object CharactersScreen : Screen {
    private fun readResolve(): Any = CharactersScreen

    override val key: ScreenKey
        get() = "Characters"

    @Composable
    override fun Content() {
        val viewModel: CharactersViewModel = koinViewModel()
        val navigator = LocalNavigator.currentOrThrow

        CharactersScreenContent(viewModel, navigator)
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun CharactersScreenContent(
    viewModel: CharactersViewModel,
    navigator: Navigator
) {
    val characters = viewModel.characters.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(characters.itemCount) { index ->
            val character = characters[index]
            character?.let { char ->
                CharacterItem(
                    character = char,
                    onOpenDetails = {
                        navigator.push(CharacterDetailsScreen(character.id))
                    }
                )
            }
        }
    }
}