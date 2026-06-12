package com.mariacarvalho.studytrack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mariacarvalho.studytrack.data.local.SubjectEntity

@Composable
fun SubjectCard(
    subject: SubjectEntity,
    sessionsCount: Int,
    onClick: () -> Unit
) {
    val iconColor = subject.color.toColor()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF1E1A36))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(iconColor.copy(alpha = 0.18f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = subject.name.firstOrNull()?.uppercase() ?: "?",
                color = iconColor,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = subject.name,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Toca para ver sessões",
                color = Color(0xFF8C86A8),
                fontSize = 13.sp
            )
        }

        Text(
            text = "$sessionsCount sessões",
            color = iconColor,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

private fun String.toColor(): Color {
    return try {
        Color(android.graphics.Color.parseColor(this))
    } catch (e: Exception) {
        Color(0xFFA78BFA)
    }
}
