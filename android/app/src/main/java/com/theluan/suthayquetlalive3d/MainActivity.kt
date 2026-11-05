package com.theluan.suthayquetlalive3d

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LiveApp()
        }
    }
}

@Composable
fun LiveApp() {
    var isLive by remember { mutableStateOf(false) }
    var likes by remember { mutableIntStateOf(0) }
    var gifts by remember { mutableIntStateOf(0) }
    var message by remember { mutableStateOf("Chào mừng đến với sân chùa!") }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF101C19)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        "SƯ THẦY QUÉT LÁ",
                        color = Color(0xFFFFD76A),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "LIVE 3D INTERACTIVE",
                        color = Color.White,
                        fontSize = 13.sp
                    )
                    Spacer(Modifier.height(20.dp))
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(330.dp)
                            .background(
                                Color(0xFF244638),
                                RoundedCornerShape(24.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("🏯", fontSize = 55.sp)
                            Text("🌳  🍂  🌳", fontSize = 35.sp)
                            Text("🧘", fontSize = 85.sp)
                            Text(
                                "SÂN CHÙA - CHẾ ĐỘ DEMO",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text("🍂  🍃  🍂", fontSize = 30.sp)
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }

                item {
                    Text(
                        message,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(12.dp))
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatCard("❤️ Tim", likes)
                        StatCard("🎁 Quà", gifts)
                    }
                    Spacer(Modifier.height(16.dp))
                }

                item {
                    Button(
                        onClick = { isLive = !isLive },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (isLive) "🔴 DỪNG DEMO LIVE" else "▶ BẮT ĐẦU DEMO LIVE")
                    }
                    Spacer(Modifier.height(10.dp))
                }

                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = {
                            likes++
                            message = "Cảm ơn bạn đã thả tim!"
                        }) {
                            Text("❤️ Thả tim")
                        }
                        Button(onClick = {
                            gifts++
                            message = "Cảm ơn món quà của bạn!"
                        }) {
                            Text("🎁 Tặng quà")
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                }

                item {
                    OutlinedButton(
                        onClick = {
                            likes = 0
                            gifts = 0
                            message = "Đã đặt lại dữ liệu DEMO"
                        }
                    ) {
                        Text("Đặt lại thử nghiệm")
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: Int) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF284A3B)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, color = Color.White)
            Text(
                value.toString(),
                color = Color(0xFFFFD76A),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
