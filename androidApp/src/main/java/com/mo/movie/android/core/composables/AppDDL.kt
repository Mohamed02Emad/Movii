import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mo.movie.android.core.composables.text.AppText

@Composable
fun <T> AppDropdown(
    modifier : Modifier = Modifier,
    hint: String,
    items: List<DDLItem<T>>,
    selectedItem: T?,
    containerColor : Color = Color.Transparent,
    selectedTextColor : Color = MaterialTheme.colorScheme.onPrimary,
    hintColor : Color =  Color(155, 155, 155, 255),
    itemTextColor : Color = MaterialTheme.colorScheme.onPrimary,
    onItemSelected: (T) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box() {
        val selected = items.firstOrNull { it.value == selectedItem }
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(8.dp))
                .background(containerColor)
                .clickable {
                    expanded = !expanded
                }
                .padding(start = 6.dp, top = 4.dp , bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
             AppText(text = selected?.title ?: hint, fontSize = 12.5.sp , fontColor = if(selected?.title == null) hintColor else selectedTextColor)
             Icon(Icons.Filled.ArrowDropDown, tint = selectedTextColor ,contentDescription = "Dropdown Menu")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = { AppText(text = item.title, fontSize = 12.sp , fontColor = itemTextColor) },
                    onClick = {
                        expanded = false
                        onItemSelected(items[index].value)
                    },
//                    item.content()
                )
            }
        }
    }
}

data class DDLItem<T>(val value: T, val title: String,)