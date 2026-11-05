package com.theluan.suthayquetlalive3d

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.sceneview.Scene
import io.github.sceneview.math.Position
import io.github.sceneview.math.Size
import io.github.sceneview.node.CameraNode
import io.github.sceneview.node.CubeNode
import io.github.sceneview.node.SphereNode
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberNode

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

    val engine = rememberEngine()
val materialLoader = io.github.sceneview.rememberMaterialLoader(engine)

val floorMaterial = remember(engine) {
    materialLoader.createColorInstance(Color(0xFF78916A))
}
val templeMaterial = remember(engine) {
    materialLoader.createColorInstance(Color(0xFFB77A42))
}
val roofMaterial = remember(engine) {
    materialLoader.createColorInstance(Color(0xFF713B28))
}
val gateMaterial = remember(engine) {
    materialLoader.createColorInstance(Color(0xFF8B4D2D))
}
val gateTopMaterial = remember(engine) {
    materialLoader.createColorInstance(Color(0xFFB98A42))
}
val monkMaterial = remember(engine) {
    materialLoader.createColorInstance(Color(0xFFE0A13D))
}
val headMaterial = remember(engine) {
    materialLoader.createColorInstance(Color(0xFFE7B98B))
}

    val floor = rememberNode(engine) {
        CubeNode(
            engine = engine,
            size = Size(7f, 0.2f, 7f),
            materialInstance = rememberColorMaterial(engine, Color(0xFF78916A))
        ).apply {
            position = Position(y = -0.5f)
        }
    }

    val templeBase = rememberNode(engine) {
        CubeNode(
            engine = engine,
            size = Size(2.8f, 1.5f, 1.8f),
            materialInstance = rememberColorMaterial(engine, Color(0xFFB77A42))
        ).apply {
            position = Position(y = 0.25f, z = -1.4f)
        }
    }

    val templeRoof = rememberNode(engine) {
        CubeNode(
            engine = engine,
            size = Size(3.5f, 0.25f, 2.4f),
            materialInstance = rememberColorMaterial(engine, Color(0xFF713B28))
        ).apply {
            position = Position(y = 1.15f, z = -1.4f)
        }
    }

    val gateLeft = rememberNode(engine) {
        CubeNode(
            engine = engine,
            size = Size(0.25f, 2f, 0.25f),
            materialInstance = rememberColorMaterial(engine, Color(0xFF8B4D2D))
        ).apply {
            position = Position(x = -1.8f, y = 0.5f, z = 1.5f)
        }
    }

    val gateRight = rememberNode(engine) {
        CubeNode(
            engine = engine,
            size = Size(0.25f, 2f, 0.25f),
            materialInstance = rememberColorMaterial(engine, Color(0xFF8B4D2D))
        ).apply {
            position = Position(x = 1.8f, y = 0.5f, z = 1.5f)
        }
    }

    val gateTop = rememberNode(engine) {
        CubeNode(
            engine = engine,
            size = Size(4.1f, 0.25f, 0.35f),
            materialInstance = rememberColorMaterial(engine, Color(0xFFB98A42))
        ).apply {
            position = Position(y = 1.55f, z = 1.5f)
        }
    }

    val monkBody = rememberNode(engine) {
        CubeNode(
            engine = engine,
            size = Size(0.65f, 1.1f, 0.45f),
            materialInstance = rememberColorMaterial(engine, Color(0xFFE0A13D))
        ).apply {
            position = Position(y = 0.15f, z = 0.5f)
        }
    }

    val monkHead = rememberNode(engine) {
        SphereNode(
            engine = engine,
            radius = 0.32f,
            materialInstance = rememberColorMaterial(engine, Color(0xFFE7B98B))
        ).apply {
            position = Position(y = 0.95f, z = 0.5f)
        }
    }

    val cameraNode = rememberCameraNode(engine) {
        position = Position(y = 3.0f, z = 9.0f)
        lookAt(Position(y = 0.3f, z = 0f))
    }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF101C19)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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

                Spacer(Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(
                            Color(0xFF263F35),
                            RoundedCornerShape(20.dp)
                        )
                ) {
                    Scene(
                        modifier = Modifier.fillMaxSize(),
                        engine = engine,
                        cameraNode = cameraNode,
                        childNodes = listOf(
                            floor,
                            templeBase,
                            templeRoof,
                            gateLeft,
                            gateRight,
                            gateTop,
                            monkBody,
                            monkHead
                        )
                    )

                    Text(
                        "SÂN CHÙA 3D • DEMO",
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(12.dp)
                            .background(
                                Color.Black.copy(alpha = 0.55f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    message,
                    color = Color.White,
                    fontSize = 14.sp
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatCard("❤️ Tim", likes)
                    StatCard("🎁 Quà", gifts)
                }

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = { isLive = !isLive },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (isLive) "🔴 DỪNG DEMO LIVE"
                        else "▶ BẮT ĐẦU DEMO LIVE"
                    )
                }

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

                    OutlinedButton(onClick = {
                        likes = 0
                        gifts = 0
                        message = "Đã đặt lại dữ liệu DEMO"
                    }) {
                        Text("Đặt lại")
                    }
                }
            }
        }
    }
}

@Composable
fun rememberColorMaterial(
    engine: com.google.android.filament.Engine,
    color: Color
): com.google.android.filament.MaterialInstance {
    val materialLoader = io.github.sceneview.rememberMaterialLoader(engine)
    return remember(color) {
        materialLoader.createColorInstance(color)
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
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, color = Color.White)
            Text(
                value.toString(),
                color = Color(0xFFFFD76A),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}