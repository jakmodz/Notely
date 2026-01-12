export default function handleApiError(error) {
  if (error.response) {
    const status = error.response.status;
    const message = error.response.data?.message;

    switch (status) {
      case 400:
        return message || 'Invalid request. Please check your input.';
      case 401:
        return message || 'Your session has expired. Please login again.';
      case 403:
        return 'You do not have permission to perform this action.';
      case 404:
        return message || 'The requested resource was not found.';
      case 500:
        return 'Server error. Please try again later.';
      default:
        return message || 'An unexpected error occurred.';
    }
  }
  return 'Network error. Please check your connection.';
}
