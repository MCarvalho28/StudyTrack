package com.mariacarvalho.studytrack.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mariacarvalho.studytrack.viewmodel.StudyViewModel

@Composable
fun SubjectDetailScreen(
    subjectId: Int,
    viewModel: StudyViewModel,
    onBackClick: () -> Unit,
    onAddSessionClick: () -> Unit
) {
    val selectedSubject by viewModel.selectedSubject.collectAsState()
    val sessions by viewModel.getSessionsBySubject(subjectId).collectAsState(initial = emptyList())
    val totalMinutes by viewModel.getTotalStudyTime(subjectId).collectAsState(initial = 0)

    val total = totalMinutes ?: 0
    val progress = when {
        total <= 0 -> 0f
        total >= 300 -> 1f
        else -> total / 300f
    }

    LaunchedEffect(subjectId) {
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
                    text = "Detalhes da disciplina",
                    color = Color(0xFF8C86A8),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF2A2448)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        tint = Color(0xFFA78BFA),
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.size(14.dp))

                Column {
                    Text(
                        text = selectedSubject?.name ?: "Disciplina",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Text(
                        text = "Adicionada recentemente",
                        color = Color(0xFF8C86A8),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DetailStatCard(
                    modifier = Modifier.weight(1f),
                    value = "$total min",
                    label = "ESTUDADO"
                )

                DetailStatCard(
                    modifier = Modifier.weight(1f),
                    value = sessions.size.toString(),
                    label = "SESSÕES"
                )

                DetailStatCard(
                    modifier = Modifier.weight(1f),
                    value = if (sessions.isEmpty()) "—" else "Hoje",
                    label = "ÚLTIMA"
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1E1A36))
                    .padding(16.dp)
            ) {
                Row {
                    Text(
                        text = "Progresso semanal",
                        color = Color(0xFF8C86A8),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "${(progress * 100).toInt()}%",
                        color = Color(0xFFA78BFA),
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(RoundedCornerShape(50)),
                    color = Color(0xFFA78BFA),
                    trackColor = Color(0xFF3A3457)
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            OutlinedButton(
                onClick = onAddSessionClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(text = "Adicionar sessão de estudo")
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = {
                    selectedSubject?.let {
                        viewModel.deleteSubject(it)
                        onBackClick()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(text = "Apagar disciplina")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                Text(
                    text = "SESSÕES DE ESTUDO",
                    color = Color(0xFF8C86A8),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "${sessions.size} sessões",
                    color = Color(0xFF5F597A),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            if (sessions.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(RoundedCornerShape(50))
                                .background(Color(0xFF1E1A36)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.HourglassEmpty,
                                contentDescription = null,
                                tint = Color(0xFF5F597A),
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Ainda não há sessões de estudo.",
                            color = Color(0xFF5F597A),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "Começa agora!",
                            color = Color(0xFF5F597A),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(sessions) { session ->
                        StudySessionCard(
                            duration = session.durationMinutes,
                            note = session.note,
                            onDeleteClick = {
                                viewModel.deleteSessionById(session.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailStatCard(
    modifier: Modifier = Modifier,
    value: String,
    label: String
) {
    Column(
        modifier = modifier
            .height(66.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF1E1A36))
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = value,
            color = Color(0xFFA78BFA),
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = label,
            color = Color(0xFF8C86A8),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
private fun StudySessionCard(
    duration: Int,
    note: String,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1E1A36))
            .padding(16.dp)
    ) {
        Text(
            text = "$duration minutos",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )

        if (note.isNotBlank()) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = note,
                color = Color(0xFF8C86A8),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onDeleteClick
        ) {
            Text(
                text = "Apagar sessão",
                color = Color(0xFFFF9F7A)
            )
        }
    }
}