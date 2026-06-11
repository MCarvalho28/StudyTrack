package com.mariacarvalho.studytrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mariacarvalho.studytrack.ui.components.SubjectCard
import com.mariacarvalho.studytrack.viewmodel.StudyViewModel

@Composable
fun HomeScreen(
    viewModel: StudyViewModel,
    onAddSubjectClick: () -> Unit,
    onSubjectClick: (Int) -> Unit
) {
    val subjects by viewModel.subjects.collectAsState(initial = emptyList())

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "StudyTrack",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Organiza as tuas disciplinas e sessões de estudo.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            Button(
                onClick = onAddSubjectClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Adicionar Disciplina")
            }

            if (subjects.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Ainda não existem disciplinas.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(top = 16.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(subjects) { subject ->
                        SubjectCard(
                            subject = subject,
                            onClick = {
                                onSubjectClick(subject.id)
                            }
                        )
                    }
                }
            }
        }
    }
}