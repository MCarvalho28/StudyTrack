package com.mariacarvalho.studytrack.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mariacarvalho.studytrack.ui.components.SubjectCard
import com.mariacarvalho.studytrack.viewmodel.StudyViewModel
import androidx.compose.foundation.lazy.items

@Composable
fun HomeScreen(
    viewModel: StudyViewModel,
    onAddSubjectClick: () -> Unit,
    onSubjectClick: (Int) -> Unit
) {
    val subjects by viewModel.subjects.collectAsState(initial = emptyList())

    val allSessions by viewModel.allSessions.collectAsState(initial = emptyList())
    val totalStudyTime by viewModel.totalStudyTime.collectAsState(initial = 0)

    val totalMinutes = totalStudyTime ?: 0
    val totalStudyText = formatStudyTime(totalMinutes)

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
            Text(
                text = "BEM-VINDO DE VOLTA",
                color = Color(0xFF8C86A8),
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Boas sessões,",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Estudante!",
                color = Color(0xFFA78BFA),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Estudado",
                    value = totalStudyText,
                    iconType = StatIcon.Timer
                )

                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Disciplinas",
                    value = subjects.size.toString(),
                    iconType = StatIcon.Book
                )

                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Sessões",
                    value = allSessions.size.toString(),
                    iconType = StatIcon.Fire
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "AS TUAS DISCIPLINAS",
                    color = Color(0xFF8C86A8),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "Ver todas",
                    color = Color(0xFFA78BFA),
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (subjects.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Ainda não existem disciplinas.",
                        color = Color(0xFF8C86A8),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(bottom = 12.dp)
                ) {
                    items(subjects) { subject ->
                        val subjectSessionsCount = allSessions.count { session ->
                            session.subjectId == subject.id
                        }

                        SubjectCard(
                            subject = subject,
                            sessionsCount = subjectSessionsCount,
                            onClick = {
                                onSubjectClick(subject.id)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onAddSubjectClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "Adicionar disciplina")
            }
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    iconType: StatIcon
) {
    Box(
        modifier = modifier
            .height(82.dp)
            .background(
                color = Color(0xFF1E1A36),
                shape = RoundedCornerShape(18.dp)
            )
            .padding(12.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )

                if (iconType == StatIcon.Fire) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = null,
                        tint = Color(0xFFFF9F7A),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = title,
                color = Color(0xFF8C86A8),
                style = MaterialTheme.typography.bodySmall
            )
        }

        if (iconType == StatIcon.Timer || iconType == StatIcon.Book) {
            Icon(
                imageVector = if (iconType == StatIcon.Timer) {
                    Icons.Default.Timer
                } else {
                    Icons.Default.Book
                },
                contentDescription = null,
                tint = Color(0xFFA78BFA),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(20.dp)
            )
        }
    }
}

private enum class StatIcon {
    Timer,
    Book,
    Fire
}

private fun formatStudyTime(totalMinutes: Int): String {
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60

    return when {
        hours > 0 && minutes > 0 -> "${hours}h ${minutes}m"
        hours > 0 -> "${hours}h"
        else -> "${minutes}m"
    }
}