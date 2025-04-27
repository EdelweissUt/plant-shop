export function formatCurrency(value: number, locale = 'vi-VN', currency = 'VND'): string {
    return new Intl.NumberFormat(locale, {
        style: 'currency',
        currency,
        maximumFractionDigits: 0,
    }).format(value);
}
export function validateEmail(email: string): boolean {
    const regex = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;
    return regex.test(email);
}
export function formatDate(date: string | Date, locale = 'vi-VN'): string {
    const d = typeof date === 'string' ? new Date(date) : date;
    return d.toLocaleDateString(locale, {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
    });
}

