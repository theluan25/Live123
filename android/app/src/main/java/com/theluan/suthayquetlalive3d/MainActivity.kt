
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
import io.github.sceneview.node.CubeNode
import io.github.sceneview.node.SphereNode
import io.github.sceneview.node.GeometryNode
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberNode
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberMainLightNode
import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.abs

private data class MonkActor(
    val id: Long,
    val name: String,
    val comment: String,
    val parts: List<GeometryNode>,
    val head: SphereNode,
    val movingParts: List<GeometryNode>,
    val originalPositions: List<Position>,
    val leaves: List<GeometryNode>,
    val leafPositions: List<Position>,
    val startOffset: Float,
    val createdAtMillis: Long
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LiveApp() }
    }
}

@Composable
fun LiveApp() {
    var isLive by remember { mutableStateOf(false) }
    var likes by remember { mutableIntStateOf(0) }
    var gifts by remember { mutableIntStateOf(0) }
    var username by remember { mutableStateOf("Khách") }
    var commentText by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("Chào mừng đến với sân chùa!") }
    var nextId by remember { mutableLongStateOf(1L) }

    val monks = remember { mutableStateListOf<MonkActor>() }

    val engine = rememberEngine()
    val materialLoader = rememberMaterialLoader(engine)

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
    val goldMaterial = remember(engine) {
        materialLoader.createColorInstance(Color(0xFFB98A42))
    }
    val robeMaterial = remember(engine) {
        materialLoader.createColorInstance(Color(0xFFE0A13D))
    }
    val robeDarkMaterial = remember(engine) {
        materialLoader.createColorInstance(Color(0xFFB96A27))
    }
    val skinMaterial = remember(engine) {
        materialLoader.createColorInstance(Color(0xFFE7B98B))
    }
    val broomMaterial = remember(engine) {
        materialLoader.createColorInstance(Color(0xFF70452B))
    }
    val strawMaterial = remember(engine) {
        materialLoader.createColorInstance(Color(0xFFD7B45B))
    }
    val leafMaterial = remember(engine) {
        materialLoader.createColorInstance(Color(0xFFB87932))
    }

    // Hoạt ảnh đi vào sân, quét chổi và lá bay
    LaunchedEffect(Unit) {
        while (true) {
            androidx.compose.runtime.withFrameNanos { frameTime ->
                val now = System.currentTimeMillis()

                monks.forEach { monk ->
                    val elapsed = now - monk.createdAtMillis

                    val progress = (elapsed / 2500f)
                        .coerceIn(0f, 1f)

                    val travelOffset =
                        monk.startOffset * progress

                    val seconds = frameTime / 1_000_000_000.0

                    val swing = sin(
                        seconds * 5.0 + monk.id
                    ).toFloat() * 0.15f

                    val allNodes = monk.parts + monk.head

                    allNodes.forEachIndexed { index, node ->
                        val original = monk.originalPositions[index]

                        val isSweepingPart = monk.movingParts.any {
                            it === node
                        }

                        node.position = Position(
                            x = original.x +
                                if (isSweepingPart && progress >= 1f) swing else 0f,
                            y = original.y,
                            z = original.z - travelOffset
                        )
                    }

                    // Lá bắt đầu bay khi sư thầy đã vào sân
                    val sweepTime = (
                        (elapsed - 2500L).coerceAtLeast(0L) / 1000f
                    )

                    monk.leaves.forEachIndexed { index, leaf ->
                        val original = monk.leafPositions[index]

                        if (progress < 1f) {
                            leaf.position = Position(
                                x = original.x,
                                y = original.y,
                                z = original.z - travelOffset
                            )
                        } else {
                            val phase = (
                                sweepTime * 4.0 +
                                index * 1.4 +
                                monk.id
                            )

                            val spread = (
                                0.06f + sweepTime.coerceAtMost(3f) * 0.08f
                            ).coerceAtMost(0.32f)

                            leaf.position = Position(
                                x = original.x +
                                    sin(phase).toFloat() * spread,
                                y = original.y +
                                    abs(sin(phase * 1.7)).toFloat() * 0.08f,
                                z = original.z - monk.startOffset +
    cos(phase).toFloat() * spread
                            )
                        }
                    }
                }
            }
        }
    }

    val floor = rememberNode {
        CubeNode(
            engine,
            size = Size(7f, 0.2f, 7f),
            materialInstance = floorMaterial
        ).apply {
            position = Position(y = -0.5f)
        }
    }

    val templeBase = rememberNode {
        CubeNode(
            engine,
            size = Size(2.8f, 1.5f, 1.8f),
            materialInstance = templeMaterial
        ).apply {
            position = Position(y = 0.25f, z = -1.4f)
        }
    }

    val templeRoof = rememberNode {
        CubeNode(
            engine,
            size = Size(3.5f, 0.25f, 2.4f),
            materialInstance = roofMaterial
        ).apply {
            position = Position(y = 1.15f, z = -1.4f)
        }
    }

    val gateLeft = rememberNode {
        CubeNode(
            engine,
            size = Size(0.25f, 2f, 0.25f),
            materialInstance = gateMaterial
        ).apply {
            position = Position(x = -1.8f, y = 0.5f, z = 1.5f)
        }
    }

    val gateRight = rememberNode {
        CubeNode(
            engine,
            size = Size(0.25f, 2f, 0.25f),
            materialInstance = gateMaterial
        ).apply {
            position = Position(x = 1.8f, y = 0.5f, z = 1.5f)
        }
    }

    val gateTop = rememberNode {
        CubeNode(
            engine,
            size = Size(4.1f, 0.25f, 0.35f),
            materialInstance = goldMaterial
        ).apply {
            position = Position(y = 1.55f, z = 1.5f)
        }
    }

    val cameraNode = rememberCameraNode(engine) {
        position = Position(y = 3f, z = 9f)
        lookAt(Position(y = 0.3f, z = 0f))
    }

    MaterialTheme {
        Surface(
            Modifier.fillMaxSize(),
            color = Color(0xFF101C19)
        ) {
            Column(
                Modifier.fillMaxSize().padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "SƯ THẦY QUÉT LÁ",
                    color = Color(0xFFFFD76A),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "LIVE 3D INTERACTIVE • COMMENT DEMO",
                    color = Color.White,
                    fontSize = 12.sp
                )

                Spacer(Modifier.height(10.dp))

                BoxWithConstraints(
                    Modifier.fillMaxWidth()
                        .weight(1f)
                        .background(
                            Color(0xFF263F35),
                            RoundedCornerShape(20.dp)
                        )
                ) {
                    Scene(
                        modifier = Modifier.fillMaxSize(),
                        engine = engine,
                        mainLightNode = rememberMainLightNode(engine) {
                            intensity = 100000f
                        },
                        cameraNode = cameraNode,
                        childNodes = listOf(
                            floor,
                            templeBase,
                            templeRoof,
                            gateLeft,
                            gateRight,
                            gateTop
                        ) + monks.flatMap {
                            it.parts + it.head + it.leaves
                        }
                    )

                    Text(
                        "SÂN CHÙA 3D • ${monks.size} SƯ THẦY",
                        Modifier.align(Alignment.TopCenter)
                            .padding(12.dp)
                            .background(
                                Color.Black.copy(alpha = 0.55f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    monks.forEach { monk ->
                        val lane = ((monk.id - 1L) % 5L).toInt()
                        val row = ((monk.id - 1L) / 5L).toInt()

                        val horizontalPosition = (lane - 2) * 0.12f
                        val verticalPosition =
                            (0.58f + row * 0.07f).coerceAtMost(0.88f)

                        Text(
                            text = monk.name,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .offset(
                                    x = maxWidth * horizontalPosition,
                                    y = maxHeight * verticalPosition
                                )
                                .background(
                                    Color(0xDD17251D),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(
                                    horizontal = 7.dp,
                                    vertical = 4.dp
                                ),
                            color = Color(0xFFFFD76A),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                Text(message, color = Color.White, fontSize = 13.sp)

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    label = { Text("Tên người bình luận") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color(0xFFFFD76A),
                        unfocusedLabelColor = Color.LightGray,
                        focusedBorderColor = Color(0xFFFFD76A),
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color(0xFFFFD76A)
                    )
                )

                Spacer(Modifier.height(5.dp))

                OutlinedTextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    label = { Text("Nội dung bình luận") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color(0xFFFFD76A),
                        unfocusedLabelColor = Color.LightGray,
                        focusedBorderColor = Color(0xFFFFD76A),
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color(0xFFFFD76A)
                    )
                )

                Spacer(Modifier.height(5.dp))

                Button(
                    onClick = {
                        val name = username.trim()
                            .ifBlank { "Khách $nextId" }

                        val comment = commentText.trim()
                            .ifBlank { "Nam mô A Di Đà Phật" }

                        val lane = ((nextId - 1L) % 5L).toInt()
                        val row = ((nextId - 1L) / 5L).toInt()

                        val x = (lane - 2) * 0.72f
                        val z = 0.35f + row * 0.65f

                        val startOffset = 2.5f
                        val startZ = z + startOffset

                        val body = CubeNode(
                            engine,
                            size = Size(0.48f, 0.85f, 0.38f),
                            materialInstance = robeMaterial
                        ).apply {
                            position = Position(x = x, y = 0f, z = startZ)
                        }

                        val robeFront = CubeNode(
                            engine,
                            size = Size(0.50f, 0.25f, 0.40f),
                            materialInstance = robeDarkMaterial
                        ).apply {
                            position = Position(
                                x = x,
                                y = -0.25f,
                                z = startZ + 0.02f
                            )
                        }

                        val head = SphereNode(
                            engine,
                            radius = 0.24f,
                            materialInstance = skinMaterial
                        ).apply {
                            position = Position(
                                x = x,
                                y = 0.62f,
                                z = startZ
                            )
                        }

                        val leftArm = CubeNode(
                            engine,
                            size = Size(0.18f, 0.62f, 0.20f),
                            materialInstance = robeMaterial
                        ).apply {
                            position = Position(
                                x = x - 0.32f,
                                y = 0.05f,
                                z = startZ
                            )
                        }

                        val rightArm = CubeNode(
                            engine,
                            size = Size(0.18f, 0.62f, 0.20f),
                            materialInstance = robeMaterial
                        ).apply {
                            position = Position(
                                x = x + 0.32f,
                                y = 0.05f,
                                z = startZ
                            )
                        }

                        val leftHand = SphereNode(
                            engine,
                            radius = 0.10f,
                            materialInstance = skinMaterial
                        ).apply {
                            position = Position(
                                x = x - 0.32f,
                                y = -0.28f,
                                z = startZ
                            )
                        }

                        val rightHand = SphereNode(
                            engine,
                            radius = 0.10f,
                            materialInstance = skinMaterial
                        ).apply {
                            position = Position(
                                x = x + 0.32f,
                                y = -0.28f,
                                z = startZ
                            )
                        }

                        val leftLeg = CubeNode(
                            engine,
                            size = Size(0.18f, 0.38f, 0.20f),
                            materialInstance = robeDarkMaterial
                        ).apply {
                            position = Position(
                                x = x - 0.13f,
                                y = -0.55f,
                                z = startZ
                            )
                        }

                        val rightLeg = CubeNode(
                            engine,
                            size = Size(0.18f, 0.38f, 0.20f),
                            materialInstance = robeDarkMaterial
                        ).apply {
                            position = Position(
                                x = x + 0.13f,
                                y = -0.55f,
                                z = startZ
                            )
                        }

                        val broomHandle = CubeNode(
                            engine,
                            size = Size(0.07f, 1.05f, 0.07f),
                            materialInstance = broomMaterial
                        ).apply {
                            position = Position(
                                x = x + 0.58f,
                                y = -0.05f,
                                z = startZ + 0.05f
                            )
                        }

                        val broomHead = CubeNode(
                            engine,
                            size = Size(0.42f, 0.18f, 0.20f),
                            materialInstance = strawMaterial
                        ).apply {
                            position = Position(
                                x = x + 0.58f,
                                y = -0.58f,
                                z = startZ + 0.05f
                            )
                        }

                        val parts: List<GeometryNode> = listOf(
                            body,
                            robeFront,
                            leftArm,
                            rightArm,
                            leftHand,
                            rightHand,
                            leftLeg,
                            rightLeg,
                            broomHandle,
                            broomHead
                        )

                        val movingParts: List<GeometryNode> = listOf(
                            rightArm,
                            rightHand,
                            broomHandle,
                            broomHead
                        )

                        // Tạo 5 chiếc lá khô quanh đầu chổi
                        val leaves: List<GeometryNode> = (0 until 5).map { i ->
                            CubeNode(
                                engine,
                                size = Size(
                                    0.12f + (i % 2) * 0.03f,
                                    0.025f,
                                    0.075f
                                ),
                                materialInstance = leafMaterial
                            ).apply {
                                position = Position(
                                    x = x + 0.55f + (i - 2) * 0.12f,
                                    y = -0.43f,
                                    z = startZ + 0.12f + (i % 2) * 0.10f
                                )
                            }
                        }

                        val allNodes = parts + head
                        val originalPositions =
                            allNodes.map { it.position }

                        val leafPositions =
                            leaves.map { it.position }

                        monks.add(
                            MonkActor(
                                id = nextId,
                                name = name,
                                comment = comment,
                                parts = parts,
                                head = head,
                                movingParts = movingParts,
                                originalPositions = originalPositions,
                                leaves = leaves,
                                leafPositions = leafPositions,
                                startOffset = startOffset,
                                createdAtMillis = System.currentTimeMillis()
                            )
                        )

                        message =
                            "$name: $comment • Đã tạo sư thầy #$nextId"

                        nextId++
                        commentText = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("💬 GỬI BÌNH LUẬN – TẠO SƯ THẦY")
                }

                Spacer(Modifier.height(5.dp))

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatCard("❤️ Tim", likes)
                    StatCard("🎁 Quà", gifts)
                    StatCard("🙏 Sư thầy", monks.size)
                }

                Spacer(Modifier.height(5.dp))

                Button(
                    onClick = {
                        isLive = !isLive
                        message = if (isLive) {
                            "Đã bật chế độ DEMO. Bình luận vẫn đang nhập thử bằng tay."
                        } else {
                            "Đã dừng phiên DEMO."
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (isLive) "🔴 DỪNG DEMO LIVE"
                        else "▶ BẮT ĐẦU DEMO LIVE"
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            likes++
                            message = "Cảm ơn bạn đã thả tim!"
                        }
                    ) {
                        Text("❤️ Thả tim")
                    }

                    Button(
                        onClick = {
                            gifts++
                            message = "Cảm ơn món quà của bạn!"
                        }
                    ) {
                        Text("🎁 Tặng quà")
                    }

                    OutlinedButton(
                        onClick = {
                            monks.clear()
                            nextId = 1L
                            likes = 0
                            gifts = 0
                            message = "Đã đặt lại dữ liệu DEMO"
                        }
                    ) {
                        Text("Đặt lại")
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
            Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, color = Color.White)
            Text(
                value.toString(),
                color = Color(0xFFFFD76A),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
