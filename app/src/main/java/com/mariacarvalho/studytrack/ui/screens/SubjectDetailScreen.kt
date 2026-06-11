package com.mariacarvalho.studytrack.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mariacarvalho.studytrack.viewmodel.StudyViewModel

@OptIn(ExperimentalMaterial3Api::class)
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

    LaunchedEffect(subjectId) {
        viewModel.loadSubjectById(subjectId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Detalhes da Disciplina")
                },
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text(text = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = selectedSubject?.name ?: "Disciplina",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tempo total estudado: ${totalMinutes ?: 0} minutos",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onAddSessionClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Adicionar Sessão de Estudo")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {
                    selectedSubject?.let {
                        viewModel.deleteSubject(it)
                        onBackClick()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Apagar Disciplina")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Sessões de estudo",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (sessions.isEmpty()) {
                Text(
                    text = "Ainda não existem sessões de estudo para esta disciplina.",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                LazyColumn {
                    items(sessions) { session ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "${session.durationMinutes} minutos",
                                    style = MaterialTheme.typography.titleMedium
                                )

                                if (session.note.isNotBlank()) {
                                    Text(
                                        text = session.note,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }

                                TextButton(
                                    onClick = {
                                        viewModel.deleteSessionById(session.id)
                                    }
                                ) {
                                    Text(text = "Apagar sessão")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}