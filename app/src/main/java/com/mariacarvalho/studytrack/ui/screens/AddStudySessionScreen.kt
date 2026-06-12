package com.mariacarvalho.studytrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mariacarvalho.studytrack.viewmodel.StudyViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddStudySessionScreen(
    subjectId: Int,
    viewModel: StudyViewModel,
    onBackClick: () -> Unit
) {
    var duration by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var selectedMood by remember { mutableStateOf("Bem") }

    val errorMessage by viewModel.errorMessage.collectAsState()
    val selectedSubject by viewModel.selectedSubject.collectAsState()

    LaunchedEffect(subjectId) {
        viewModel.clearError()
        viewModel.loadSubjectById(subjectId)
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
                    text = "Adicionar sessão",
                    color = Color(0xFF8C86A8),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Nova sessão",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "A registar para ${selectedSubject?.name ?: "disciplina"}",
                color = Color(0xFF8C86A8),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "DURAÇÃO",
                color = Color(0xFF8C86A8),
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = duration,
                onValueChange = { duration = it },
                placeholder = {
                    Text(
                        text = "Ex: 45 minutos",
                        color = Color(0xFF5F597A)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                isError = errorMessage != null,
                singleLine = true,
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

            Spacer(modifier = Modifier.height(12.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DurationChip("15 min") { duration = "15" }
                DurationChip("30 min") { duration = "30" }
                DurationChip("45 min") { duration = "45" }
                DurationChip("1h") { duration = "60" }
                DurationChip("1h30") { duration = "90" }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "COMO CORREU?",
                color = Color(0xFF8C86A8),
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MoodCard(
                    modifier = Modifier.weight(1f),
                    emoji = "😓",
                    label = "Difícil",
                    selected = selectedMood == "Difícil",
                    onClick = { selectedMood = "Difícil" }
                )

                MoodCard(
                    modifier = Modifier.weight(1f),
                    emoji = "🙂",
                    label = "Normal",
                    selected = selectedMood == "Normal",
                    onClick = { selectedMood = "Normal" }
                )

                MoodCard(
                    modifier = Modifier.weight(1f),
                    emoji = "😊",
                    label = "Bem",
                    selected = selectedMood == "Bem",
                    onClick = { selectedMood = "Bem" }
                )

                MoodCard(
                    modifier = Modifier.weight(1f),
                    emoji = "🚀",
                    label = "Excelente",
                    selected = selectedMood == "Excelente",
                    onClick = { selectedMood = "Excelente" }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "NOTA (OPCIONAL)",
                color = Color(0xFF8C86A8),
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                placeholder = {
                    Text(
                        text = "O que estudaste hoje?",
                        color = Color(0xFF5F597A)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color(0xFF1E1A36),
                    unfocusedContainerColor = Color(0xFF1E1A36),
                    focusedBorderColor = Color(0xFFA78BFA),
                    unfocusedBorderColor = Color(0xFF3A3457)
                ),
                shape = RoundedCornerShape(10.dp)
            )

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = {
                    val finalNote = if (note.isBlank()) {
                        "Sessão marcada como: $selectedMood"
                    } else {
                        "$note | Estado: $selectedMood"
                    }

                    viewModel.addStudySession(
                        subjectId = subjectId,
                        durationText = duration,
                        note = finalNote
                    )

                    if ((duration.toIntOrNull() ?: 0) > 0) {
                        onBackClick()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(text = "Guardar sessão")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "A sessão será adicionada ao teu histórico",
                color = Color(0xFF5F597A),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun DurationChip(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFF1E1A36),
                shape = RoundedCornerShape(50)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color(0xFFA78BFA),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun MoodCard(
    modifier: Modifier = Modifier,
    emoji: String,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (selected) Color(0xFFA78BFA) else Color(0xFF3A3457)

    Column(
        modifier = modifier
            .height(70.dp)
            .background(
                color = Color(0xFF1E1A36),
                shape = RoundedCornerShape(14.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp)
            )
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = emoji)

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            color = if (selected) Color.White else Color(0xFF8C86A8),
            style = MaterialTheme.typography.labelSmall
        )
    }
}