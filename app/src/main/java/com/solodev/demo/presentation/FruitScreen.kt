package com.solodev.demo.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

import com.solodev.demo.R
import com.solodev.demo.domain.model.Dimensions
import com.solodev.demo.domain.model.Meta
import com.solodev.demo.domain.model.Product
import com.solodev.demo.domain.model.Review
import com.solodev.demo.ui.theme.SoloDevDemoTheme

@Composable
fun FruitScreen(products: LazyPagingItems<Product>) {

    val context = LocalContext.current

    LaunchedEffect(key1 = products.loadState) {
        if (products.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (products.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if(products.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                items(products.itemCount){ product ->
                    products[product]?.let { p->
                        ProductItem(
                            product = p,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                item {
                    if(products.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier,

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = product.title,
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                product.title?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                product.description?.let {
                    Text(
                        text = it,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                product.category?.let {
                    Text(
                        text = it,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                product.price?.let {
                    Text(
                        text = it.toString(),
                        modifier = Modifier.fillMaxWidth(),

                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FruitItemPreview() {
    SoloDevDemoTheme {
        ProductItem(
            product = Product(
                id = 1,
                title = "Apple",
                description = "Rosaceae",
                category = "Rosales",
                price = 49.99,
                discountPercentage = 0.32,
                rating = 4.85,
                stock = 17,
                tags = listOf("fragrances","perfumes"),
                brand = "Calvin Klein",
                sku = "DZM2JQZE",
                weight = 5,
                dimensions = Dimensions(width = 11.53, height = 14.44, depth = 6.81),
                warrantyInformation = "1 year",
                shippingInformation = "Free shipping",
                availabilityStatus = "In stock",
                reviews = listOf(Review(rating = 5, comment = "Great value for money!", date = "2024-05-23T08:56:21.619Z", reviewerName = "Sophia Brown", reviewerEmail = "sophia.brown@x.dummyjson.com")),
                returnPolicy = "No return policy",
                minimumOrderQuantity = 1,
                meta = Meta(createdAt = "", updatedAt = "", barcode = "", qrCode = ""),
                images = listOf(),
                thumbnail = ""
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}