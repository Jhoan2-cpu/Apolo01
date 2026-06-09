package com.apolo.tracking.presentation.billing

// Mock data — the case study does not provide a billing endpoint

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apolo.tracking.presentation.components.AppTopBar
import com.apolo.tracking.ui.theme.BluePrimary
import com.apolo.tracking.ui.theme.CyanSecondary
import com.apolo.tracking.ui.theme.SurfaceLight
import com.apolo.tracking.ui.theme.TextSecondary

private data class MockInvoice(
    val number: String,
    val description: String,
    val amount: String,
    val date: String,
    val isPaid: Boolean
)

private val mockInvoices = listOf(
    MockInvoice("FAC-001", "Envío Lima - Callao",     "S/ 150.00", "2026-05-01", true),
    MockInvoice("FAC-002", "Envío Lima - Miraflores", "S/ 200.00", "2026-05-03", false),
    MockInvoice("FAC-003", "Envío Lima - San Isidro", "S/ 175.00", "2026-05-05", true),
    MockInvoice("FAC-004", "Envío Lima - Surco",      "S/ 225.00", "2026-05-07", false),
    MockInvoice("FAC-005", "Envío Lima - Barranco",   "S/ 180.00", "2026-05-10", true),
    MockInvoice("FAC-006", "Envío Lima - Chorrillos", "S/ 320.00", "2026-05-12", false),
)

private val pendingTotal = "S/ 745.00"
private val pendingCount = mockInvoices.count { !it.isPaid }
private val paidCount    = mockInvoices.count { it.isPaid }

@Composable
fun BillingScreen(onLogout: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceLight)
    ) {
        AppTopBar(title = "Billings", onLogout = onLogout)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { SummaryCard() }
            item { PayButton() }
            item {
                Text(
                    text = "Facturas Recientes",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = BluePrimary,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            items(mockInvoices) { invoice ->
                InvoiceCard(
                    invoice = invoice,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun SummaryCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                brush = Brush.linearGradient(listOf(BluePrimary, CyanSecondary)),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(24.dp)
    ) {
        Column {
            Text(
                text = "Total a pagar",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = pendingTotal,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SummaryStatItem(
                    label = "Total",
                    value = "${mockInvoices.size}"
                )
                SummaryDivider()
                SummaryStatItem(
                    label = "Pendientes",
                    value = "$pendingCount"
                )
                SummaryDivider()
                SummaryStatItem(
                    label = "Pagadas",
                    value = "$paidCount"
                )
            }
        }
    }
}

@Composable
private fun SummaryStatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.75f),
            fontSize = 12.sp
        )
    }
}

@Composable
private fun SummaryDivider() {
    Box(
        modifier = Modifier
            .height(36.dp)
            .width(1.dp)
            .background(Color.White.copy(alpha = 0.3f))
    )
}

@Composable
private fun PayButton() {
    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
    ) {
        Icon(
            imageVector = Icons.Default.CreditCard,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Pagar ahora",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}

@Composable
private fun InvoiceCard(invoice: MockInvoice, modifier: Modifier = Modifier) {
    val statusColor = if (invoice.isPaid) Color(0xFF4CAF50) else Color(0xFFFF9800)
    val statusLabel = if (invoice.isPaid) "Pagado" else "Pendiente"

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(BluePrimary.copy(alpha = 0.08f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Receipt,
                    contentDescription = null,
                    tint = BluePrimary,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = invoice.number,
                    fontWeight = FontWeight.Bold,
                    color = BluePrimary,
                    fontSize = 14.sp
                )
                Text(
                    text = invoice.description,
                    color = TextSecondary,
                    fontSize = 12.sp
                )
                Text(
                    text = invoice.date,
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = invoice.amount,
                    fontWeight = FontWeight.Bold,
                    color = BluePrimary,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .background(statusColor.copy(alpha = 0.12f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = statusLabel,
                        color = statusColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}
