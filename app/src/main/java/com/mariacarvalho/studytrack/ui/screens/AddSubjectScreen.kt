package com.mariacarvalho.studytrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.mariacarvalho.studytrack.data.local.SubjectEntity
import com.mariacarvalho.studytrack.ui.components.SubjectCard
import com.mariacarvalho.studytrack.viewmodel.StudyViewModel

@Composable
fun AddSubjectScreen(
    viewModel: StudyViewModel,
    onBackClick: () -> Unit
) {
    var subjectName by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf(Icons.Default.School) }
    var selectedColor by remember { mutableStateOf(Color(0xFFA78BFA)) }

    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.clearError()
    }

    Scaffold(
        containerColor = Color(0xFF0F0D19)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0F0D19))
                .padding(paddingValues)
                .padding(22.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onBackClick,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(modifier = Modifier.size(6.dp))

                    Text(text = "Voltar")
                }

                Spacer(modifier = Modifier.size(12.dp))

                Text(
                    text = "Adicionar disciplina",
                    color = Color(0xFF8C86A8),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Nova disciplina",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Personaliza e dá um nome à tua disciplina",
                color = Color(0xFF8C86A8),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "NOME",
                color = Color(0xFF8C86A8),
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = subjectName,
                onValueChange = { subjectName = it },
                placeholder = {
                    Text(
                        text = "Ex: Matemática",
                        color = Color(0xFF5F597A)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = errorMessage != null,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color(0xFF1E1A36),
                    unfocusedContainerColor = Color(0xFF1E1A36),
                    focusedBorderColor = Color(0xFFA78BFA),
                    unfocusedBorderColor = Color(0xFF3A3457),
                    errorBorderColor = MaterialTheme.colorScheme.error
                ),
                shape = RoundedCornerShape(10.dp)
            )

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "ÍCONE",
                color = Color(0xFF8C86A8),
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(10.dp))

            IconGrid(
                selectedIcon = selectedIcon,
                onIconSelected = { selectedIcon = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "COR",
                color = Color(0xFF8C86A8),
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(10.dp))

            ColorSelector(
                selectedColor = selectedColor,
                onColorSelected = { selectedColor = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "PRÉ-VISUALIZAÇÃO",
                color = Color(0xFF8C86A8),
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(10.dp))

            PreviewSubjectCard(
                name = subjectName.ifBlank { "Nova disciplina" },
                icon = selectedIcon,
                color = selectedColor
            )

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = {
                    viewModel.addSubject(
                        name = subjectName,
                        color = selectedColor.toHex()
                    )

                    if (subjectName.isNotBlank()) {
                        onBackClick()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = subjectName.isNotBlank(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White,
                    disabledContentColor = Color(0xFF5F597A)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(text = "Guardar disciplina")
            }
        }
    }
}

@Composable
private fun IconGrid(
    selectedIcon: ImageVector,
    onIconSelected: (ImageVector) -> Unit
) {
    val icons = listOf(
        Icons.Default.School,
        Icons.Default.Science,
        Icons.Default.MenuBook,
        Icons.Default.Language,
        Icons.Default.Palette,
        Icons.Default.MusicNote,
        Icons.Default.Code,
        Icons.Default.Favorite,
        Icons.Default.Memory,
        Icons.Default.Spa
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        icons.chunked(5).forEach { rowIcons ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowIcons.forEach { icon ->
                    IconOption(
                        icon = icon,
                        selected = icon == selectedIcon,
                        onClick = {
                            onIconSelected(icon)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun IconOption(
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (selected) Color(0xFFA78BFA) else Color(0xFF3A3457)

    Box(
        modifier = Modifier
            .size(46.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1E1A36))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (selected) Color(0xFFA78BFA) else Color(0xFF8C86A8),
            modifier = Modifier.size(22.dp)
        )
    }
}

@Composable
private fun ColorSelector(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    val colors = listOf(
        Color(0xFFA78BFA),
        Color(0xFF5EEAD4),
        Color(0xFFFF9F7A),
        Color(0xFFF9A8D4),
        Color(0xFF93C5FD),
        Color(0xFF84CC16),
        Color(0xFFF59E0B)
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        colors.forEach { color ->
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(color)
                    .border(
                        width = if (color == selectedColor) 3.dp else 0.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
                    .clickable {
                        onColorSelected(color)
                    }
            )
        }
    }
}

@Composable
private fun PreviewSubjectCard(
    name: String,
    icon: ImageVector,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFF1E1A36))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(color.copy(alpha = 0.18f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(modifier = Modifier.size(14.dp))

        Column {
            Text(
                text = name,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Toca para ver detalhes",
                color = Color(0xFF8C86A8),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

private fun Color.toHex(): String {
    val red = (red * 255).toInt()
    val green = (green * 255).toInt()
    val blue = (blue * 255).toInt()

    return "#%02X%02X%02X".format(red, green, blue)
}