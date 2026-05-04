import 'package:flutter/material.dart';
import '../utils/theme.dart';

// ── Stat Card ─────────────────────────────────────────────────────────────────
class StatCard extends StatelessWidget {
  final String emoji;
  final String value;
  final String label;
  final Color color;

  const StatCard({
    super.key,
    required this.emoji,
    required this.value,
    required this.label,
    required this.color,
  });

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: Card(
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            children: [
              Text(emoji, style: const TextStyle(fontSize: 24)),
              const SizedBox(height: 6),
              Text(value, style: TextStyle(fontSize: 24, fontWeight: FontWeight.w900, color: color)),
              const SizedBox(height: 4),
              Text(label, style: const TextStyle(fontSize: 11, color: kTextSec), textAlign: TextAlign.center),
            ],
          ),
        ),
      ),
    );
  }
}

// ── Badge ─────────────────────────────────────────────────────────────────────
class StatusBadge extends StatelessWidget {
  final String label;
  final Color color;

  const StatusBadge({super.key, required this.label, required this.color});

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 4),
      decoration: BoxDecoration(
        color: color.withOpacity(0.12),
        borderRadius: BorderRadius.circular(99),
      ),
      child: Text(
        label,
        style: TextStyle(fontSize: 11, fontWeight: FontWeight.w700, color: color),
      ),
    );
  }
}

// ── Section Header ────────────────────────────────────────────────────────────
class SectionHeader extends StatelessWidget {
  final String title;
  final String? action;
  final VoidCallback? onAction;

  const SectionHeader({super.key, required this.title, this.action, this.onAction});

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        Text(title, style: const TextStyle(fontSize: 15, fontWeight: FontWeight.w700, color: kText)),
        if (action != null)
          GestureDetector(
            onTap: onAction,
            child: Text(action!, style: const TextStyle(fontSize: 12, color: kPrimary, fontWeight: FontWeight.w600)),
          ),
      ],
    );
  }
}

// ── Empty State ───────────────────────────────────────────────────────────────
class EmptyState extends StatelessWidget {
  final String emoji;
  final String message;

  const EmptyState({super.key, required this.emoji, required this.message});

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Padding(
        padding: const EdgeInsets.all(40),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Text(emoji, style: const TextStyle(fontSize: 40)),
            const SizedBox(height: 12),
            Text(message, style: const TextStyle(color: kTextMuted, fontSize: 14), textAlign: TextAlign.center),
          ],
        ),
      ),
    );
  }
}

// ── Info Row ──────────────────────────────────────────────────────────────────
class InfoRow extends StatelessWidget {
  final String label;
  final String value;

  const InfoRow({super.key, required this.label, required this.value});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          SizedBox(
            width: 140,
            child: Text(label, style: const TextStyle(fontSize: 13, color: kTextMuted)),
          ),
          Expanded(
            child: Text(value, style: const TextStyle(fontSize: 13, fontWeight: FontWeight.w600, color: kText)),
          ),
        ],
      ),
    );
  }
}

// ── App Card ──────────────────────────────────────────────────────────────────
class AppCard extends StatelessWidget {
  final Widget child;
  final EdgeInsets? padding;

  const AppCard({super.key, required this.child, this.padding});

  @override
  Widget build(BuildContext context) {
    return Card(
      child: Padding(
        padding: padding ?? const EdgeInsets.all(16),
        child: child,
      ),
    );
  }
}

// ── Quick Access Button ───────────────────────────────────────────────────────
class QuickButton extends StatelessWidget {
  final String emoji;
  final String label;
  final Color color;
  final VoidCallback onTap;

  const QuickButton({
    super.key,
    required this.emoji,
    required this.label,
    required this.color,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: Container(
        padding: const EdgeInsets.all(16),
        decoration: BoxDecoration(
          color: kCard,
          borderRadius: BorderRadius.circular(14),
          border: Border.all(color: color.withOpacity(0.3), width: 1.5),
        ),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Text(emoji, style: const TextStyle(fontSize: 26)),
            const SizedBox(height: 6),
            Text(label,
              style: TextStyle(fontSize: 11, fontWeight: FontWeight.w700, color: color),
              textAlign: TextAlign.center,
            ),
          ],
        ),
      ),
    );
  }
}