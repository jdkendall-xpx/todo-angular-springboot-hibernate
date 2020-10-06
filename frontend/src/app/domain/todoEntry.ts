export interface TodoEntry {
  id: number;
  title: string;
  description: string;
  createdAt: Date;
  completed: boolean;
  lastModifiedAt?: Date;
  completedOn?: Date;
  dueOn?: Date;
}
