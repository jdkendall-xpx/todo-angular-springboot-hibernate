export interface TodoEntry {
  id: number;
  title: string;
  description: string;
  createdAt: Date;
  completed: boolean;
  lastModified?: Date;
  completedOn?: Date;
  dueOn?: Date;
}
