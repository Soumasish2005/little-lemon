package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun Home(
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val menuDao = remember(database) { database.menuDao() }
    val menuLiveData = remember(menuDao) { menuDao.getAll() }
    val menuItems by menuLiveData.observeAsState(emptyList())

    var searchPhrase by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    val filteredItems = remember(menuItems, searchPhrase, selectedCategory) {
        var items = menuItems
        if (searchPhrase.isNotBlank()) {
            items = items.filter {
                it.title.contains(searchPhrase, ignoreCase = true)
            }
        }
        if (selectedCategory.isNotBlank()) {
            items = items.filter {
                it.category.equals(selectedCategory, ignoreCase = true)
            }
        }
        items
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Little Lemon Logo",
                        modifier = Modifier.height(50.dp),
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .clickable {
                            navController.navigate(Profile.route)
                        },
                )
            }
        }

        item {
            HeroSection(
                searchPhrase = searchPhrase,
                onSearchPhraseChange = { searchPhrase = it },
            )
        }

        item {
            OrderForDelivery(
                menuItems = menuItems,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
            )
        }

        items(
            items = filteredItems,
            key = { it.id },
        ) { item ->
            MenuItem(item = item)
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color.LightGray,
            )
        }
    }
}

@Composable
fun HeroSection(
    searchPhrase: String,
    onSearchPhraseChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF495E57))
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 24.dp),
    ) {
        Text(
            text = "Little Lemon",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFF4CE14),
        )
        Text(
            text = "Chicago",
            fontSize = 24.sp,
            color = Color.White,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp),
            )
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Hero Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp)),
            )
        }

        TextField(
            value = searchPhrase,
            onValueChange = onSearchPhraseChange,
            placeholder = { Text("Enter search phrase", color = Color.Gray) },
            textStyle = TextStyle(color = Color.Black),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color(0xFF495E57),
                )
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color(0xFFEAEAEA),
                unfocusedContainerColor = Color(0xFFEAEAEA),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(8.dp)),
        )
    }
}

@Composable
fun OrderForDelivery(
    menuItems: List<MenuItemRoom>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
) {
    val categories = remember(menuItems) {
        menuItems
            .map { it.category.replaceFirstChar { char -> char.uppercase() } }
            .distinct()
            .ifEmpty { listOf("Starters", "Mains", "Desserts", "Drinks") }
    }
    val isDark = isSystemInDarkTheme()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp),
    ) {
        Text(
            text = "ORDER FOR DELIVERY!",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = if (isDark) Color.White else Color(0xFF333333),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            for (category in categories) {
                val isSelected = selectedCategory.equals(category, ignoreCase = true)
                Button(
                    onClick = {
                        if (isSelected) onCategorySelected("") else onCategorySelected(category)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) Color(0xFF495E57) else Color(0xFFEDEFEE),
                        contentColor = if (isSelected) Color.White else Color(0xFF495E57),
                    ),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text(
                        text = category,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                    )
                }
            }
        }
        HorizontalDivider(
            color = Color.LightGray,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(item: MenuItemRoom) {
    val localDrawableRes = remember(item.title) {
        when {
            item.title.contains("Greek", ignoreCase = true) -> R.drawable.greek_salad
            item.title.contains("Bruschetta", ignoreCase = true) -> R.drawable.bruschetta
            item.title.contains("Fish", ignoreCase = true) -> R.drawable.grilled_fish
            item.title.contains("Pasta", ignoreCase = true) -> R.drawable.pasta
            item.title.contains("Lemon", ignoreCase = true) -> R.drawable.lemon_dessert
            else -> null
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp),
        ) {
            Text(
                text = item.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = item.description,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp),
            )
            Text(
                text = "$${String.format(java.util.Locale.US, "%.2f", item.price)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF495E57),
            )
        }
        if (localDrawableRes != null) {
            Image(
                painter = painterResource(id = localDrawableRes),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )
        } else {
            GlideImage(
                model = item.image,
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    LittleLemonTheme {
        Home()
    }
}
